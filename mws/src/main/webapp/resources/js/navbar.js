!function($){
    var NavBar = function(element,options){
        var g = this;
        $.extend(g, $.fn.navbar.defaults, options);
        g.element = $(element).on('click','span[data-page]',function(){
            g.go($(this).attr('data-page'));
        });
        if(g.autoQuery){
            g.query();
        }else{
            g.render();
        }
    }
    $.extend(NavBar.prototype,{
        render: function(){
            var g = this,
                html = ['<ul class="pagination pagination-sm">',
                    '<li><span data-page="1" aria-label="Previous">&laquo;</span></li>'],
                total = Math.min(g.totalPage,5),
                current = g.page||1,
                min = Math.max(current - Math.floor((total-1)/2),1),
                max = min + total - 1;
            if(max > g.totalPage){
                min = Math.max(1,min + g.totalPage - max);
                max = g.totalPage;
            }
            for(var i = min;i <= max;i++){
                html.push('<li><span data-page="',i,'"',(i==current?' class="selected"':''),'>',i,'</span></li>');
            }
            html.push('<li><span data-page="',g.totalPage,'" aria-label="Next">&raquo;</span></li>','</ul>');
            g.element.html(html.join(''));
        },
        first: function(){
            this.go(1);
        },
        last: function(){
            this.go(this.totalPage);
        },
        prev: function(){
            this.go(this.page - 1);
        },
        next: function(){
            this.go(this.page + 1);
        },
        go: function(page){
            if(this.page == page) return;
            this.query(page);
        },
        query: function(page,params){
            var g = this,
                parms=$.extend({
                    page: g.page = page||1,
                    pagesize: g.pagesize
                },$.isFunction(g.parms)?g.parms.call(g):g.parms,params);
            $.ajax({
                type:'post',
                url:g.url,
                data:parms,
                cache: false,
                dataType: 'json',
                success: function (data) {
                    if(data.success){
                        g.totalPage = Math.ceil(data.total/g.pagesize);
                        g.onLoad(data);
                        g.render()
                    }
                },
                error: function (XMLHttpRequest, textStatus) {
                    
                }
            })
        }
    });
    $.fn.navbar = function(option){
        return this.each(function(){
            var $this = $(this),
                data = $this.data('navbar'),
                options = typeof option == 'object' && option;
            if(!data) {$this.data('navbar', (data = new NavBar(this, options)));}
            if (typeof option == 'string') {data[option]();}
        });
    }
    $.fn.query = function(page,parms){
        return this.each(function(){
            var $this = $(this),
                data = $this.data('navbar');
            if(data) {
                data.query(page,parms);
            }
        });
    }
    $.fn.navbar.defaults = {
        url:'',
        pagesize:10,
        totalPage:1,
        parms:{},
        autoQuery:true,
        onLoad: function(datas){}
    }
}(window.jQuery);