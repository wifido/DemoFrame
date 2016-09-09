var dialog_str = "<div id='dialog' title='' style='display: none'></div>";
var label_str = "<label></label>";
var notNull_textInput_str = "<input type='text' class='form-control' notNull>";
var pcomp_title_name_textInput_str = "<input type='text' class='form-control' name='pcomp_title_tile_name' notNull>";
var textInput_str = "<input type='text' class='form-control'>";
var notNull_fileInput_str = "<input class='file' type='file' notNull>";
var fileInput_str = "<input class='file' type='file'>";
$(document).ready(function () {
    $("input[name='pcomp_title_tile_name']").blur(validatePcompTitle);
});
function validatePcomp() {
    var requiredInput = $("input[notNull]")
    for (i = 0; i < requiredInput.length; i++) {
        if (isNull(requiredInput[i].value)) {
            $(requiredInput[i]).attr("style", "border-color: #f9cf0b;border-width:5px");
            $(requiredInput[i]).attr("title", "不能为空");
            $(requiredInput[i]).tooltip({
                hide: {
                    effect: "explode",
                    delay: 250
                }
            });
            $(requiredInput[i]).trigger('mouseover');
            return false;
        } else {
            $(requiredInput[i]).attr("style", "");
            $(requiredInput[i]).attr("title", "");
            if (!validatePcompTitle()) {
                return false;
            }
        }
    }
    return true;
}
function validatePcompTitle() {
    var titleInput = $("input[name='pcomp_title_tile_name']")[0];
    var ifExists = $.pcomp.title.ifExists(titleInput.value);
    if (ifExists) {
        $(titleInput).attr("style", "border-color: #f9cf0b;border-width:5px");
        $(titleInput).attr("title", "已存在");
        $(titleInput).tooltip({
            hide: {
                effect: "explode",
                delay: 250
            }
        });
        $(titleInput).trigger('mouseover');
        return false;
    }
    return true;
}