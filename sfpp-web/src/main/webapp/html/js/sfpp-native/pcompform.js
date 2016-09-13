var dialog_str = "<div id='dialog' title='' style='display: none'></div>";
var label_str = "<label></label>";
var notNull_textInput_str = "<input type='text' class='form-control' notNull>";
var pcomp_title_name_textInput_str = "<input type='text' class='form-control' name='pcomp_title_tile_name' notNull>";
var pcomp_kind_name_textInput_str = "<input type='text' class='form-control' name='pcomp_kind_name' notNull>";
var pcomp_software_name_textInput_str = "<input type='text' class='form-control' name='pcomp_software_name' notNull>";
var textInput_str = "<input type='text' class='form-control'>";
var notNull_fileInput_str = "<input class='file' type='file' notNull>";
var fileInput_str = "<input class='file' type='file'>";
var md_div = "<div><textarea style='display:none'></textarea></div>";
$(document).ready(function () {
    $("input[name='pcomp_title_tile_name']").blur(validatePcompTitle);
});
function validatePcomp(parameter) {
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
            if (!validatePcompTitle() || !validatePcompKind(parameter) || !validatePcompSoftware(parameter)) {
                return false;
            }
        }
    }
    return true;
}
function validatePcompTitle() {
    if ($("input[name='pcomp_title_tile_name']").length == 0) {
        return true;
    }
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
function validatePcompKind(titlename) {
    if (isNull(titlename) || $("input[name='pcomp_kind_name']").length == 0) {
        return true;
    }
    var kindInput = $("input[name='pcomp_kind_name']")[0];
    var ifExists = $.pcomp.kind.exists(titlename, kindInput.value);
    if (ifExists) {
        $(kindInput).attr("style", "border-color: #f9cf0b;border-width:5px");
        $(kindInput).attr("title", "已存在");
        $(kindInput).tooltip({
            hide: {
                effect: "explode",
                delay: 250
            }
        });
        $(kindInput).trigger('mouseover');
        return false;
    }
    return true;
}
function validatePcompSoftware(kindId) {
    if (isNull(kindId) || $("input[name='pcomp_software_name']").length == 0) {
        return true;
    }
    var softwareInput = $("input[name='pcomp_software_name']")[0];
    var ifExists = $.pcomp.software.exists(kindId, softwareInput.value);
    if (ifExists) {
        $(softwareInput).attr("style", "border-color: #f9cf0b;border-width:5px");
        $(softwareInput).attr("title", "已存在");
        $(softwareInput).tooltip({
            hide: {
                effect: "explode",
                delay: 250
            }
        });
        $(softwareInput).trigger('mouseover');
        return false;
    }
    return true;
}