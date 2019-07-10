package cn.edu.mju.utils;

//excel表格操作工具类

import cn.edu.mju.entity.Department;
import cn.edu.mju.entity.Employee;
import cn.edu.mju.entity.Information;
import cn.edu.mju.entity.Position;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.biff.WritableWorkbookImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.SQLOutput;
import java.util.*;

public class ExcelUtils {



    public static class Input{

        public static Map<String,Object> input(InputStream inputStream) throws IOException, BiffException {

            Map<String,Object> map = new HashMap<>();
            List<Employee> list = new ArrayList<>();
            WorkbookSettings workbookSettings = new WorkbookSettings();
            workbookSettings.setEncoding("UTF-8"); //关键代码，解决中文乱码

                Workbook workbook = Workbook.getWorkbook(inputStream,workbookSettings);

                boolean flag = true;
                Sheet sheet = workbook.getSheet(0);
                String name="";
                String department="";
                String position="";
                tag:
                for(int i=0;i<sheet.getRows();i++){

                    for (int j = 0;j<sheet.getColumns();j++){

                        String contents = sheet.getCell(j, i).getContents();
                        if(i==0&&flag){
                            // TODO: 2019/6/2
                            if("id".equals(contents)){
//                                System.out.print("id ");
                                flag = true;
                            }else if("姓名".equals(contents)){
                                flag = true;
//                                System.out.print("name ");
                            }else if("部门".equals(contents)){
                                flag = true;
//                                System.out.print("department ");
                            }else if("职位".equals(contents)){
                                flag = true;
//                                System.out.print("position ");
                            }else{
//                                System.out.println("false ");
                                flag = false;
                                break tag;
                            }
                        }

                        if(i>0&&flag){

//                            System.out.print("value"+i+" ");
                            if(j==1){

                                name = contents;
                            }

                            if(j==2){
                                department = contents;
                            }

                            if(j==3){
                                position = contents;
                            }
                        }

                    }

                    Department department1 = new Department();
                    department1.setName(department);
                    Position position1 = new Position();
                    position1.setName(position);
                    String createTime = String.valueOf(System.currentTimeMillis());

                    Employee employee = new Employee(name,createTime,department1,position1);
                    list.add(employee);
//                    System.out.println();
                }

                map.put("success",flag);
                map.put("employees",list);

            return map;
        }


    }


    //导出
    public static class JXLExport{

        public static void export(OutputStream outputStream,Map<String,Object> map){
//            File file = new File("/upload/psm.xls");
            try{
                String[] title = {"id","姓名","部门","职位"};

                List<Employee> employees = (List<Employee>) map.get("employees");

//                //创建文件
//                file.getParentFile().mkdirs();
//                file.createNewFile();

                //创建工作薄
                WritableWorkbook writableWorkbook = Workbook.createWorkbook(outputStream);

                //创建sheet
                WritableSheet writableSheet = writableWorkbook.createSheet("sheet1",0);

                Label label = null;

                for (int i=0;i<title.length;i++) {
                    //第几列第几行写数据

                    label = new Label(i,0,title[i]);

                    writableSheet.addCell(label);


                }

                //追加数据

                for (int i=0;i<employees.size();i++){

                    label = new Label(0,i+1,employees.get(i).getId().toString());

                    writableSheet.addCell(label);

                    label = new Label(1,i+1,employees.get(i).getName());

                    writableSheet.addCell(label);
                    label = new Label(2,i+1,employees.get(i).getDepartment().getName());

                    writableSheet.addCell(label);
                    label = new Label(3,i+1,employees.get(i).getPosition().getName());

                    writableSheet.addCell(label);

                }
                writableWorkbook.write();
                writableWorkbook.close();

            }catch(Exception exception){
//                exception.printStackTrace();
            }

        }


    }


    public static class DetailExport {
        public static void export(OutputStream outputStream, Map<String, Object> map) {
//            File file = new File("/upload/psm.xls");
            try {
                String[] title = {"id", "姓名", "身份证号", "电话", "性别","地址","是否已婚","邮箱","学历","出生日期","员工id"};

                List<Information> informations = (List<Information>) map.get("informations");

//                //创建文件
//                file.getParentFile().mkdirs();
//                file.createNewFile();

                //创建工作薄
                WritableWorkbook writableWorkbook = Workbook.createWorkbook(outputStream);

                //创建sheet
                WritableSheet writableSheet = writableWorkbook.createSheet("sheet1", 0);

                Label label = null;

                for (int i = 0; i < title.length; i++) {
                    //第几列第几行写数据

                    label = new Label(i, 0, title[i]);

                    writableSheet.addCell(label);


                }

                //追加数据

                for (int i = 0; i < informations.size(); i++) {

                    label = new Label(0, i + 1,String.valueOf(informations.get(i).getId()));

                    writableSheet.addCell(label);

                    label = new Label(1, i + 1, informations.get(i).getName());

                    writableSheet.addCell(label);
                    label = new Label(2, i + 1, informations.get(i).getIdCard());

                    writableSheet.addCell(label);
                    label = new Label(3, i + 1, informations.get(i).getPhone());

                    writableSheet.addCell(label);

                    Integer sex = informations.get(i).getSex();
                    String s = "";

                    if(sex==0){
                        s = "男";
                    }else if(sex==1){
                        s = "女";
                    }else {
                        s = "保密";
                    }
                    Integer ismarried = informations.get(i).getIsmarried();
                    String m = "";

                    if(ismarried==0){
                        m = "是";
                    }else if(ismarried==1){
                        m = "否";
                    }else {
                        m = "保密";
                    }


                    label = new Label(4, i + 1,s );

                    writableSheet.addCell(label);
                    label = new Label(5, i + 1, informations.get(i).getAddress());

                    writableSheet.addCell(label);
                    label = new Label(6, i + 1, m);

                    writableSheet.addCell(label);
                    label = new Label(7, i + 1, informations.get(i).getEmail());

                    writableSheet.addCell(label);
                    label = new Label(8, i + 1, informations.get(i).getEdu());

                    writableSheet.addCell(label);
                    label = new Label(9, i + 1, informations.get(i).getDate());

                    writableSheet.addCell(label);
                    label = new Label(10, i + 1, informations.get(i).getEid().toString());

                    writableSheet.addCell(label);

//                    WorkbookSettings workbookSettings = new WorkbookSettings();
//                    workbookSettings.setEncoding("GBK");
                }
                writableWorkbook.write();
                writableWorkbook.close();

            } catch (Exception exception) {
//                exception.printStackTrace();
            }


        }
    }

}
