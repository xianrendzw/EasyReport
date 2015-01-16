/**
 * form autofill (jQuery plugin)
 * Version: 0.1
 * Released: 2011-11-30
 * 
 * Copyright (c) 2011 Creative Area
 * Dual licensed under the MIT or GPL Version 2 licenses.
 */
(function(b){b.fn.extend({autofill:function(h,f){var d={findbyname:!0,restrict:!0},g=this;f&&b.extend(d,f);return this.each(function(){b.each(h,function(e,c){var a;if(d.findbyname)a='[name="'+e+'"]',a=d.restrict?g.find(a):b(a),1==a.length?a.val("checkbox"==a.attr("type")?[c]:c):1<a.length?a.val([c]):(a='[name="'+e+'[]"]',a=d.restrict?g.find(a):b(a),a.each(function(){b(this).val(c)}));else if(a="#"+e,a=d.restrict?g.find(a):b(a),1==a.length)a.val("checkbox"==a.attr("type")?[c]:c);else{var f=!1;a=d.restrict? g.find('input:radio[name="'+e+'"]'):b('input:radio[name="'+e+'"]');a.each(function(){f=!0;if(this.value==c)this.checked=!0});f||(a=d.restrict?g.find('input:checkbox[name="'+e+'[]"]'):b('input:checkbox[name="'+e+'[]"]'),a.each(function(){b(this).val(c)}))}})})}})})(jQuery);