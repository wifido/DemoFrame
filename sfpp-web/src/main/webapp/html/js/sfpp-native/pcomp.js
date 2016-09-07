$.pcomp = {};
$.pcomp.version = {
    newSoftwareDownload: function (platform, fn) {
        $.ajaxFileUpload
        (
            {
                url: getContextPath() + "/pcomp/pcomp_version/update",
                type: 'post',
                data: {pcomp_version_id: versionId, pcomp_version_introduction: introduction},
                secureuri: false, //一般设置为false
                fileElementId: 'file',
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data)  //服务器成功响应处理函数
                {
                    handleSuccess(data);
                },
                error: function (data)//服务器响应失败处理函数
                {
                    alert("连接异常");
                }
            }
        )
        return false;
    },
    updateIntroduction: function (versionId, introduction) {
        $.ajaxFileUpload
        (
            {
                url: getContextPath() + "/pcomp/pcomp_version/update",
                type: 'post',
                data: {pcomp_version_id: versionId, pcomp_version_introduction: introduction},
                secureuri: false, //一般设置为false
                fileElementId: 'fakeFileInput',
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data)  //服务器成功响应处理函数
                {
                    handleSuccess(data);
                },
                error: function (data)//服务器响应失败处理函数
                {
                    alert("连接异常");
                }
            }
        )
        return false;
    },
    updateQuickStart: function (versionId, quickstart) {
        $.ajaxFileUpload
        (
            {
                url: getContextPath() + "/pcomp/pcomp_version/update",
                type: 'post',
                data: {pcomp_version_id: versionId, pcomp_version_quickstart: quickstart},
                secureuri: false, //一般设置为false
                fileElementId: 'fakeFileInput',
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data)  //服务器成功响应处理函数
                {
                    handleSuccess(data);
                },
                error: function (data)//服务器响应失败处理函数
                {
                    alert("连接异常");
                }
            }
        )
        return false;
    },
    updateVersionNumber: function (versionId, versionNumber, fn) {
        $.ajaxFileUpload
        (
            {
                url: getContextPath() + "/pcomp/pcomp_version/update",
                type: 'post',
                data: {pcomp_version_id: versionId, pcomp_version_number: versionNumber},
                secureuri: false, //一般设置为false
                fileElementId: 'fakeFileInput',
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data)  //服务器成功响应处理函数
                {
                    handleSuccess(data);
                    fn();
                },
                error: function (data)//服务器响应失败处理函数
                {
                    alert("连接异常");
                }
            }
        )
    }
};
$.pcomp.software = {
    delete: function (pcompVersionId, pcompSoftwareId) {
        var a = "";
        $.ajax({
            url: getContextPath() + "/pcomp/pcomp_software/remove?pcomp_software=" + pcompSoftwareId,
            async: false,
            success: function (response) {
                if (response.message != "") {
                    window.location.href = getContextPath() + "/html/pcompKind.html?pcompKindId=" + pcompVersionId;
                } else {
                    a = response.data
                }
            }
        });
        return a;
    },
    getBySoftwareId: function (pcompSoftwareId) {
        var a = "";
        $.ajax({
            url: getContextPath() + "/pcomp/software/getById?pcompSoftwareId=" + pcompSoftwareId,
            async: false,
            success: function (response) {
                if (response.message != "") {
                    alert(response.message);
                    return;
                } else {
                    a = response.data
                }
            }
        });
        return a;
    },
    getRecommended: function () {
        var a = "";
        $.ajax({
            url: getContextPath() + "/pcomp/software/recommended",
            async: false,
            success: function (response) {
                if (response.message != "") {
                    alert(response.message);
                    return;
                } else {
                    a = response.data
                }
            }
        });
        return a;
    },
    getHot: function () {
        var a = "";
        $.ajax({
            url: getContextPath() + "/pcomp/software/recommended",
            async: false,
            success: function (response) {
                if (response.message != "") {
                    alert(response.message);
                    return;
                } else {
                    a = response.data;
                }
            }
        });
        return a;
    },
    getRecommendedByKind: function (kindId) {
        var a = "";
        $.ajax({
            url: getContextPath() + "/pcomp/software/recommended?pcompKindId=" + kindId,
            async: false,
            success: function (response) {
                if (response.message != "") {
                    alert(response.message);
                    return;
                } else {
                    a = response.data
                }
            }
        });
        return a;
    },
    getHotByKind: function (kindId) {
        var a = "";
        $.ajax({
            url: getContextPath() + "/pcomp/software/recommended?pcompKindId=" + kindId,
            async: false,
            success: function (response) {
                if (response.message != "") {
                    alert(response.message);
                    return;
                } else {
                    a = response.data;
                }
            }
        });
        return a;
    },
    getAllByKind: function (kindId, pageNumber) {
        var a = "";
        $.ajax({
            url: getContextPath() + "/pcomp/software/getAllByKind?pcompKindId=" + kindId + "&pageNumber=" + pageNumber,
            async: false,
            success: function (response) {
                if (response.message != "") {
                    alert(response.message);
                    return;
                } else {
                    a = response.data
                }
            }
        });
        return a;
    },
    updateSoftwareIntroduction: function (softwareId, introduction) {
        $.ajaxFileUpload
        (
            {
                url: getContextPath() + "/pcomp/pcomp_software/update",
                type: 'post',
                data: {pcomp_software_id: softwareId, pcomp_software_introduction: introduction},
                secureuri: false, //一般设置为false
                fileElementId: 'fakeFileInput',
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data)  //服务器成功响应处理函数
                {
                    handleSuccess(data);
                },
                error: function (data)//服务器响应失败处理函数
                {
                    alert("连接异常");
                }
            }
        )
        return false;
    },
    updateSoftwareAvatar: function (softwareId, softwareName, fn) {
        $.ajaxFileUpload
        (
            {
                url: getContextPath() + "/pcomp/pcomp_software/update",
                type: 'post',
                data: {pcomp_software_id: softwareId, pcomp_software_name: softwareName},
                secureuri: false, //一般设置为false
                fileElementId: 'file',
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data)  //服务器成功响应处理函数
                {
                    handleSuccess(data);
                    fn();
                },
                error: function (data)//服务器响应失败处理函数
                {
                    alert("连接异常");
                }
            }
        )
    }
};
function handleSuccess(data) {
    var d = eval('(' + data + ')')
    if (d.data) {
        alert("修改成功");
    } else {
        alert("修改失败" + d.message);
    }
}
$.pcomp.kind = {
    getRecommended: function () {
        var a;
        $.ajax({
            url: getContextPath() + "/pcomp/kind/recommended",
            async: false,
            success: function (response) {
                if (response.message != "") {
                    alert(response.message);
                    return;
                } else {
                    a = response.data;
                }
            }
        });
        return a;
    },
    getByKindId: function (kindId) {
        var a;
        $.ajax({
            url: getContextPath() + "/pcomp/kind/getById?pcompKindId=" + kindId,
            async: false,
            success: function (response) {
                if (response.message != "") {
                    alert(response.message);
                    return;
                } else {
                    a = response.data;
                }
            }
        });
        return a;
    }
};


