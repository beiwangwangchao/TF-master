!function($){
    var Spinner = function(element,options){
        var g = this;
        $.extend(g, $.fn.spinner.defaults, options);
        g.element = $(element);
        g.render();
    }
    $.extend(Spinner.prototype,{
        render: function(){
            var g = this,
                max = g.max,
                min = g.min,
                step = g.step,
                allowdecimals = String(step).indexOf('.') != -1,
                allownegative = null == min || min < 0,
                restrict = '0123456789',reg,
                ov = Number(g.element.val()||(null == g.min?(null == max?0:Math.min(max,0)):Math.max(min,0)));
            if(allowdecimals){
                restrict +='.'
            }
            if(allownegative){
                restrict +='-';
            }
            reg = new RegExp('['+restrict+']');
            g.element.val(ov).addClass('form-control').css({'text-align':'center'})
                .wrap('<div class="input-group"></div>')
                .before(["<span class='input-group-btn'>",
                     "<button class='btn-sub btn btn-default' type='button' style='width: 26px;height:34px;'>",
                         "<div style='font-size: 18px; position: relative; top: -4px; left: -9px;'>－</div>",
                     "</button>",
                "</span>"].join(''))
                .after(["<span class='input-group-btn'>",
                    "<button class='btn-add btn btn-default' type='button' style='width: 26px;height:34px;'>",
                        "<div style='font-size: 18px; position: relative; top: -4px; left: -9px;'>＋</div>",
                    "</button>",
                "</span>"].join('')).on('contextmenu',function(e){
                    e.preventDefault();
                })/*.keydown(function(e){
                    if(e.ctrlKey && e.keyCode == 86){//黏贴
                        e.preventDefault();
                    }
                })*/.keypress(function(e){
                    var code = e.charCode,
                        k = e.keyCode;
                    if((e.type == 'keypress' && e.ctrlKey) || 
                        k == 8 || k == 46 || 
                        k == 9 || k == 13 || 
                        k == 40 || k == 27 ||
                        k == 16 || k == 17 ||
                      (k >= 18 && k <= 20) ||
                      (k >= 33 && k <= 35) ||
                      (k >= 36 && k <= 39) ||
                      (k >= 44 && k <= 45))return;
                    if(!reg.test(String.fromCharCode(code))){
                        e.preventDefault();
                    }
                }).change(function(e){
                    var v = g.element.val();
                    if(isNaN(v)){
                        g.element.val(ov);
                    }else{
                        ov = $.trim(v);
                        if(!allowdecimals && ov.indexOf('.') != -1){
                            ov = String(parseInt(ov));
                        }
                        if(!allownegative && ov.indexOf('-') != -1){
                            ov = Math.abs(ov);
                        }
                        if(null != max && ov > max){
                            ov = max;
                        }
                        if(null != min && ov < min){
                            ov = min;
                        }
                        g.element.val(ov = Number(ov));
                    }
                }).parent().on('click','.btn',function(){
                    var nv = ov+($(this).is('.btn-add')?step:-step);
                    if((null != max && nv > max) || (null != min && nv < min))return;
                    g.element.val(ov = nv).trigger($.Event('change'));
                });
        }
    });
    $.fn.spinner = function(option){
        return this.each(function(){
            var $this = $(this),
                data = $this.data('spinner'),
                options = typeof option == 'object' && option;
            if(!data) {$this.data('spinner', (data = new Spinner(this, options)));}
            if (typeof option == 'string') {data[option]();}
        });
    }
    $.fn.spinner.defaults = {
        step:1,
        min:0,
        max:null
    }
}(window.jQuery);