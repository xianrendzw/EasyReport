/*
 * jQuery gentleSelect plugin
 * http://shawnchin.github.com/jquery-gentleSelect
 *
 * Copyright (c) 2010 Shawn Chin. 
 * Licensed under the MIT license.
 * 
 * Usage:
 *  (JS)
 *
 *  $('#myselect').gentleSelect(); // default. single column
 * 
 * $('#myselect').gentleSelect({ // 3 columns, 150px wide each
 *    itemWidth : 150,
 *    columns   : 3,
 * });
 * 
 *  (HTML)
 *  <select id='myselect'><options> .... </options></select>
 *
 */
(function($) {
    
    var defaults = {
        minWidth  : 100, // only applies if columns and itemWidth not set
        itemWidth : undefined,
        columns   : undefined,
        rows      : undefined,
        title     : undefined,
        prompt    : "Make A Selection",
        maxDisplay: 0,  // set to 0 for unlimited
        openSpeed       : 400,
        closeSpeed      : 400,
        openEffect      : "slide",
        closeEffect     : "slide",
        hideOnMouseOut  : true
    }

    function defined(obj) {
        if (typeof obj == "undefined") { return false; }
        else { return true; }
    }

    function hasError(c, o) {
        if (defined(o.columns) && defined(o.rows)) {
            $.error("gentleSelect: You cannot supply both 'rows' and 'columns'");
            return true;
        }
        if (defined(o.columns) && !defined(o.itemWidth)) {
            $.error("gentleSelect: itemWidth must be supplied if 'columns' is specified");
            return true;
        }
        if (defined(o.rows) && !defined(o.itemWidth)) {
            $.error("gentleSelect: itemWidth must be supplied if 'rows' is specified");
            return true;
        }
        if (!defined(o.openSpeed) || typeof o.openSpeed != "number" && 
                (typeof o.openSpeed == "string" && (o.openSpeed != "slow" && o.openSpeed != "fast"))) { 
            $.error("gentleSelect: openSpeed must be an integer or \"slow\" or \"fast\"");
            return true;
        }
        if (!defined(o.closeSpeed) || typeof o.closeSpeed != "number" && 
                (typeof o.closeSpeed == "string" && (o.closeSpeed != "slow" && o.closeSpeed != "fast"))) { 
            $.error("gentleSelect: closeSpeed must be an integer or \"slow\" or \"fast\"");
            return true;
        }
        if (!defined(o.openEffect) || (o.openEffect != "fade" && o.openEffect != "slide")) {
            $.error("gentleSelect: openEffect must be either 'fade' or 'slide'!");
            return true;
        }
        if (!defined(o.closeEffect)|| (o.closeEffect != "fade" && o.closeEffect != "slide")) {
            $.error("gentleSelect: closeEffect must be either 'fade' or 'slide'!");
            return true;
        }
        if (!defined(o.hideOnMouseOut) || (typeof o.hideOnMouseOut != "boolean")) {
            $.error("gentleSelect: hideOnMouseOut must be supplied and either \"true\" or \"false\"!");
            return true;
        }
        return false;
    }

    function optionOverrides(c, o) {
        if (c.attr("multiple")) {
            o.hideOnMouseOut = true; // must be true or dialog will never hide
        }
    }

    function getSelectedAsText(elemList, opts) { 
        // If no items selected, return prompt
        if (elemList.length < 1) { return opts.prompt; }

        // Truncate if exceed maxDisplay
        if (opts.maxDisplay != 0 && elemList.length > opts.maxDisplay) {
            var arr = elemList.slice(0, opts.maxDisplay).map(function(){return $(this).text();});
            arr.push("...");
        } else {
            var arr = elemList.map(function(){return $(this).text();});
        }
        return arr.get().join(", ");
    }

    var methods = {
        init : function(options) {
            var o = $.extend({}, defaults, options);

            if (hasError(this, o)) { return this; }; // check for errors
            optionOverrides(this, o); // 
            this.hide(); // hide original select box
            
            // initialise <span> to replace select box
            label_text = getSelectedAsText(this.find(":selected"), o);
            var label = $("<span class='gentleselect-label'>" + label_text + "</span>")
                .insertBefore(this)
                .bind("mouseenter.gentleselect", event_handlers.labelHoverIn)
                .bind("mouseleave.gentleselect", event_handlers.labelHoverOut)
                .bind("click.gentleselect", event_handlers.labelClick)
                .data("root", this);
            this.data("label", label)
                .data("options", o);
            
            // generate list of options
            var ul = $("<ul></ul>");
            this.find("option").each(function() { 
                var li = $("<li>" + $(this).text() + "</li>")
                    .data("value", $(this).attr("value"))
                    .data("name", $(this).text())
                    .appendTo(ul);
                if ($(this).attr("selected")) { li.addClass("selected"); } 
            });

            // build dialog box
            var dialog = $("<div class='gentleselect-dialog'></div>")
                .append(ul)
                .insertAfter(label)
                .bind("click.gentleselect", event_handlers.dialogClick)
                .bind("mouseleave.gentleselect", event_handlers.dialogHoverOut)
                .data("label", label)
                .data("root", this);
            this.data("dialog", dialog);
           
            // if to be displayed in columns
            if (defined(o.columns) || defined(o.rows)) {

                // Update CSS
                ul.css("float", "left")
                    .find("li").width(o.itemWidth).css("float","left");
                    
                var f = ul.find("li:first");
                var actualWidth = o.itemWidth 
                    + parseInt(f.css("padding-left")) 
                    + parseInt(f.css("padding-right"));
                var elemCount = ul.find("li").length;
                if (defined(o.columns)) {
                    var cols = parseInt(o.columns);
                    var rows = Math.ceil(elemCount / cols);
                } else {
                    var rows = parseInt(o.rows);
                    var cols = Math.ceil(elemCount / rows);
                }
                dialog.width(actualWidth * cols);

                // add padding
                for (var i = 0; i < (rows * cols) - elemCount; i++) {
                    $("<li style='float:left' class='gentleselect-dummy'><span>&nbsp;</span></li>").appendTo(ul);
                }

                // reorder elements
                var ptr = [];
                var idx = 0;
                ul.find("li").each(function() {
                    if (idx < rows) { 
                        ptr[idx] = $(this); 
                    } else {
                        var p = idx % rows;
                        $(this).insertAfter(ptr[p]);
                        ptr[p] = $(this);
                    }
                    idx++;
                });
            } else if (typeof o.minWidth == "number") {
                dialog.css("min-width", o.minWidth);
            }

            if (defined(o.title)) {
                $("<div class='gentleselect-title'>" + o.title + "</div>").prependTo(dialog);
            }

            // ESC key should hide all dialog boxes
            $(document).bind("keyup.gentleselect", event_handlers.keyUp);

            return this;
        },

        // if select box was updated externally, we need to bring everything
        // else up to speed.
        update : function() {
            var opts = this.data("options");

            // Update li with selected data
            var v = (this.attr("multiple")) ? this.val() : [this.val()];
            $("li", this.data("dialog")).each(function() {
                var $li = $(this);
                var isSelected = ($.inArray($li.data("value"), v) != -1);
                $li.toggleClass("selected", isSelected);
            });

            // Update label
            var label = getSelectedAsText(this.find(":selected"), opts);
            this.data("label").text(label);
            
            return this;
        }
    };

    var event_handlers = {

        labelHoverIn : function() { 
            $(this).addClass('gentleselect-label-highlight'); 
        },

        labelHoverOut : function() { 
            $(this).removeClass('gentleselect-label-highlight'); 
        },

        labelClick : function() {
            var $this = $(this);
            var pos = $this.position();
            var root = $this.data("root");
            var opts = root.data("options");
            var dialog = root.data("dialog")
                .css("top", pos.top + $this.height())
                .css("left", pos.left + 1);
            if (opts.openEffect == "fade") {
                dialog.fadeIn(opts.openSpeed);
            } else {
                dialog.slideDown(opts.openSpeed);
            }
        },
    
        dialogHoverOut : function() {
            var $this = $(this);
            if ($this.data("root").data("options").hideOnMouseOut) {
                $this.hide();
            }
        },

        dialogClick : function(e) {
            var clicked = $(e.target);
            var $this = $(this);
            var root = $this.data("root");
            var opts = root.data("options");
            if (!root.attr("multiple")) {
                if (opts.closeEffect == "fade") {
                    $this.fadeOut(opts.closeSpeed);
                } else {
                    $this.slideUp(opts.closeSpeed);
                }
            }

            if (clicked.is("li") && !clicked.hasClass("gentleselect-dummy")) {
                var value = clicked.data("value");
                var name = clicked.data("name");
                var label = $this.data("label")

                if ($this.data("root").attr("multiple")) {
                    clicked.toggleClass("selected");
                    var s = $this.find("li.selected");
                    label.text(getSelectedAsText(s, opts));
                    var v = s.map(function(){ return $(this).data("value"); });
                    // update actual selectbox and trigger change event
                    root.val(v.get()).trigger("change");
                } else {
                    $this.find("li.selected").removeClass("selected");
                    clicked.addClass("selected");
                    label.text(clicked.data("name"));
                    // update actual selectbox and trigger change event
                    root.val(value).trigger("change");
                }
            }
        },

        keyUp : function(e) {
            if (e.keyCode == 27 ) { // ESC
                $(".gentleselect-dialog").hide();
            }
        }
    };

    $.fn.gentleSelect = function(method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || ! method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.gentleSelect' );
        }   
    };


})(jQuery);
