var tree = {
    controTree:function () {
        $(".list-group-item").click(function(){
            // jquery对象的回调方法中的this关键字为DOM对象
            // $(DOM) ==> JQuery
            if ( $(this).find("ul") ) { // 3 li
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
    }
}