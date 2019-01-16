//将TreeView封装成插件的方式
(function($){
    $.fn.extend({
        TreeView:function(obj){
            if($.type(obj)==="object"){
                if(obj["Default"]!=undefined){
                    var objDiv=$("<div></div>");
                    $.each(obj["Default"],function(k,v){
                        var nodes=$("<div></div>").addClass("nodes");
                        var parentNodes=$("<div></div>").addClass("parentNodes");
                        var childNodes=$("<div></div>").addClass("childNodes").css("display","none");
                        var topplus=$("<img src=\"image/topplus.gif\"/>").addClass("plus").css({"width":"9px","height":"9px","margin":"5px 4px 8px 0"});;
                        var checkbox=$("<input type=\"checkbox\"/>").addClass("checkbox").css("display",obj["checkbox"]);
                        var a=$("<span class=\"text\"><a href=\""+v["url"]+"\">"+v["name"]+"</a></span>").addClass("text");
                        parentNodes.append(topplus.attr("id",v["ajaxData"])).append(checkbox).append(a);
                        nodes.append(parentNodes).append(childNodes);
                        objDiv.append(nodes);
                    });
                    $(this).append(objDiv.html());
                }else{
                    return false;
                }
                //对TreeView里面所以的收缩的图标进行监听
                $(this).delegate(".plus","click",function(){
                    var childNodes=$(this).parent().next(),thisObj=$(this);
                    if(childNodes.css("display")!="none"){
                       TreeFn.childHide(thisObj);
                    }else{
                        if(childNodes.html()==""){
                            TreeFn.GetData(thisObj,obj);
                        }else{
                            TreeFn.childShow(thisObj);
                        }
                    }
                    //同时对checkbox也进行监听
                }).delegate(".checkbox","click",function(){
                    var checkbox=$(this),childNodes=$(this).parent().next();
                    if(checkbox.attr("checked")){
                        childNodes.find(".checkbox").attr("checked",true);
                    }else{
                        childNodes.find(".checkbox").attr("checked",false);
                    }
                })  
            }else{
                alert("111");
            }
            return this;
        }
    });
})(jQuery);
//这里定义一个对象，把我们用的到的方法封装以来
var TreeFn={
    //将ajax动态加载好的子节点展开出来
    childShow:function(thisObj){
        var childNodes=thisObj.parent().next();
        childNodes.show(); 
        if(thisObj.attr("src").indexOf("top")>=0){
            thisObj.attr("src","image/topminus.gif"); 
        }else if(thisObj.attr("src").indexOf("end")>=0){
            thisObj.attr("src","image/endminus.gif");
        }else{
            thisObj.attr("src","image/centerminus.gif");
        }
    },
    //将子节点收缩
    childHide:function(thisObj){
        var childNodes=thisObj.parent().next();
        childNodes.hide();
        if(thisObj.attr("src").indexOf("top")>=0){
            thisObj.attr("src","image/topplus.gif"); 
        }else if(thisObj.attr("src").indexOf("end")>=0){
            thisObj.attr("src","image/endplus.gif");
        }else{
            thisObj.attr("src","image/centerplus.gif");
        }
    },
    //利用父节点元素的个数，构造子节点元素的个数
    lineImage:function(thisobj){
        var objDiv=$("<div></div>");
        var p=thisobj.parent().children();
        p.each(function(index){
            if(index<p.size()-3){
                var line=$("<img src=\"image/line.gif\"/>").addClass("line");
                var nul=$("<img src=\"image/null.gif\"/>").addClass("null");
                if($(this).attr("class")=="line"||$(this).attr("class")=="join"||$(this).attr("src").indexOf("center")>=0){
                    objDiv.append(line);
                }else{
                    objDiv.append(nul);  
                }
            }
        });
        return objDiv;
    },
    //解析ajax过来的json数据，将其构造成子节点
    AnalyJSON:function(json,thisObj,obj){
        var objDiv=$("<div></div>");
        $.each(json,function(key,value){
            var nodes=$("<div></div>").addClass("nodes");
            var parentNodes=$("<div></div>").addClass("parentNodes");
            var childNodes=$("<div></div>").addClass("childNodes").css("display","none");
            var line=$("<img src=\"image/line.gif\"/>").addClass("line");
            var topplus=$("<img src=\"image/topplus.gif.gif\" />").addClass("plus");
            var centerplus=$("<img src=\"image/centerplus.gif\" />").addClass("plus").attr("id",value["id"]);
            var endplus=$("<img src=\"image/endplus.gif\"/>").addClass("plus").attr("id",value["id"]);
            var join=$("<img src=\"image/join.gif\"/>").addClass("join");
            var lineh=$("<img src=\"image/lineh.gif\"/>").addClass("end");
            var end=$("<img src=\"image/end.gif\"/>").addClass("end");
            var checkbox=$("<input type=\"checkbox\"/>").addClass("checkbox").css("display",obj["checkbox"]);
            var a=$("<span class=\"text\"><a href=\""+value["url"]+"\">"+value["name"]+"</a></span>").addClass("text");
             
            var commone=TreeFn.lineImage(thisObj);
            if(value["hasChild"]=="1"){
                if(value["last"]==undefined||value["last"]!="1")
                {
                    parentNodes.append(commone.html()).append(join).append(centerplus);
                    if(thisObj.next().attr("checked")){
                        parentNodes.append(checkbox.attr("checked",true)).append(a);
                    }else{
                        parentNodes.append(checkbox).append(a);
                    }
                }else{
                    parentNodes.append(commone.html()).append(end).append(endplus);
                    if(thisObj.next().attr("checked")){
                        parentNodes.append(checkbox.attr("checked",true)).append(a);
                    }else{
                        parentNodes.append(checkbox).append(a);
                    }
                }
                nodes.append(parentNodes);
                nodes.append(childNodes);
                objDiv.append(nodes);
            }else{
                if(value["last"]==undefined||value["last"]!="1"){
                    parentNodes.append(commone.html()).append(join).append(lineh);
                    if(thisObj.next().attr("checked")){
                        parentNodes.append(checkbox.attr("checked",true)).append(a);
                    }else{
                        parentNodes.append(checkbox).append(a);
                    }
                }else{
                    parentNodes.append(commone.html()).append(end).append(lineh);
                    if(thisObj.next().attr("checked")){
                        parentNodes.append(checkbox.attr("checked",true)).append(a);
                    }else{
                        parentNodes.append(checkbox).append(a);
                    }
                }
                nodes.append(parentNodes);
                nodes.append(childNodes);
                objDiv.append(nodes);
            }
        });
        return objDiv;
    },
    //点击父节点展开，进获取下面的子节点数据
    GetData:function(thisObj,obj){
        $.ajax({
            type:"GET",
            cache:false,
            async:false,
            url:obj["ajaxUrl"],
            dataType:"text",
            data:{Key:thisObj.attr("id")},
            beforeSend:function(){
                thisObj.parent().append("<img src=\"image/Loading.gif\" class=\"load\"/>");
            },
            success:function(json){
                thisObj.parent().next().html("").append(TreeFn.AnalyJSON($.parseJSON(json),thisObj,obj).html());
                TreeFn.childShow(thisObj);
            },
            complete:function(){
               thisObj.parent().children().remove(".load"); 
            }
        });
    }
};