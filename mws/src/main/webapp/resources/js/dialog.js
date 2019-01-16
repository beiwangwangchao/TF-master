!function($){
    var tplt = ['<div class="modal" tabindex="-1" data-backdrop="static" data-keyboard="false">',
    '<div class="modal-dialog" role="document">',
    '<div class="modal-content">',
        '<div class="modal-header">',
            '<h4 class="modal-title"></h4>',
        '</div>',
    '<div class="modal-body">',
        '<div class="modal-dialog-image"/>',
        '<div class="modal-dialog-content"/>',
    '</div>',
    '<div class="modal-footer">',
    '</div>'];
    $.dialog = {
        success:function(title,message,callback){
           var dislog = $(tplt.join('')).appendTo($('body'));
           dislog.find('.modal-title').html(title);
           dislog.find('.modal-dialog-image').addClass('modal-dialog-image-donne');
           dislog.find('.modal-dialog-content').html(message);
           dislog.find('.modal-footer').html('<button data-dismiss="modal" class="btn btn-primary">'+$l('sys.hand.btn.ok')+'</button>');
           dislog.modal('show').on('hide.bs.modal',function(e){
               if(callback && callback() == false){
                   e.preventDefault();
                   return;
               }
               dislog.remove();
           });
        },
        error:function(title,message,callback){
            var dislog = $(tplt.join('')).appendTo($('body'));
            dislog.find('.modal-title').html(title);
            dislog.find('.modal-dialog-image').addClass('modal-dialog-image-error');
            dislog.find('.modal-dialog-content').html(message);
            dislog.find('.modal-footer').html('<button data-dismiss="modal" class="btn btn-primary">'+$l('sys.hand.btn.ok')+'</button>');
            dislog.modal('show').on('hide.bs.modal',function(e){
                if(callback && callback() == false){
                    e.preventDefault();
                    return;
                }
                dislog.remove();
            });
        },
        warning:function(title,message,callback){
            var dislog = $(tplt.join('')).appendTo($('body'));
            dislog.find('.modal-title').html(title);
            dislog.find('.modal-dialog-image').addClass('modal-dialog-image-warn');
            dislog.find('.modal-dialog-content').html(message);
            dislog.find('.modal-footer').html('<button data-dismiss="modal" class="btn btn-primary">'+$l('sys.hand.btn.ok')+'</button>');
            dislog.modal('show').on('hide.bs.modal',function(e){
                if(callback && callback() == false){
                    e.preventDefault();
                    return;
                }
                dislog.remove();
            });
        },
        confirm:function(title,message,callback,callback2){
            var dislog = $(tplt.join('')).appendTo($('body')),
                footer = dislog.find('.modal-footer');
            dislog.find('.modal-title').html(title);
            dislog.find('.modal-dialog-image').addClass('modal-dialog-image-question');
            dislog.find('.modal-dialog-content').html(message);
            $('<button class="btn btn-primary">'+$l('sys.hand.btn.ok')+'</button>').appendTo(footer).click(function(){
                if(callback && callback() == false){
                    e.preventDefault();
                    return;
                }
                dislog.modal('hide');
            });
            $('<button class="btn btn-default">'+$l('sys.hand.btn.cancel')+'</button>').appendTo(footer).click(function(){
                if(callback2 && callback2() == false){
                    e.preventDefault();
                    return;
                }
                dislog.modal('hide');
            });
            dislog.modal('show').on('hide.bs.modal',function(e){
                dislog.remove();
            });;
        }
    }
}(window.jQuery);