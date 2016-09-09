$.pcomp = {};
$.pcomp.userRight = {
    getHasModifyTitleRight: function () {
        var result;
        $.ajax({
            url: getContextPath() + "/pcomp/title/addKind/hasRight",
            async: false,
            success: function (response) {
                if (response.data != null) {
                    result = response.data;
                } else {
                    alert(response.message);
                }
            }
        });
        return result;
    },
    getHasAddKindRight: function () {
        var result;
        $.ajax({
            url: getContextPath() + "/pcomp/title/addKind/hasRight",
            async: false,
            success: function (response) {
                if (response.data != null) {
                    result = response.data;
                } else {
                    alert(response.message);
                }
            }
        });
        return result;
    }
};
$.pcomp.title = {
    getAll: function () {
        var result;
        $.ajax({
            url: getContextPath() + "/pcomp/pcomp_title/get",
            async: false,
            success: function (response) {
                if (!isNull(response.message)) {
                    alert(response.message)
                } else {
                    result = response.data;
                }
            }
        });
        return result;
    },
    ifExists: function (titlename) {
        var result;
        $.ajax({
            url: getContextPath() + "/pcomp/pcomp_title/validate?pcomp_title_title_name=" + titlename,
            async: false,
            success: function (response) {
                if (!isNull(response.message)) {
                    alert(response.message)
                } else {
                    result = response.data;
                }
            }
        });
        return result;
    },
    add: function (titlename, fn) {
        var result;
        $.ajax({
            url: getContextPath() + "/pcomp/pcomp_title/create?pcomp_title_title_name=" + titlename,
            async: false,
            success: function (response) {
                if (!isNull(response.message)) {
                    alert(response.message)
                } else {
                    result = response.data;
                    fn()
                }
            }
        });
        return result;
    },
    update: function (titleId, titlename, fn) {
        var result;
        $.ajax({
            url: getContextPath() + "/pcomp/pcomp_title/update?pcomp_title_title_name=" + titlename + "&pcomp_title=" + titleId,
            async: false,
            success: function (response) {
                if (!isNull(response.message)) {
                    alert(response.message)
                } else {
                    result = response.data;
                    fn()
                }
            }
        });
        return result;
    }
};
$.pcomp.version = {
    delete: function (softwareId, versionId, fn) {
        var a = "";
        $.ajax({
            url: getContextPath() + "/pcomp/pcomp_version/index/remove?pcomp_version=" + versionId + "&pcomp_software=" + softwareId,
            async: false,
            success: function (response) {
                if (!response.data) {
                    alert(response.message)
                } else {
                    fn();
                }
            }
        });
        return a;
    },
    deleteSoftwareDownload: function (softwareId, versionId, softwareDownloadId, fn) {
        var a = "";
        $.ajax({
            url: getContextPath() + "/pcomp/pcomp_version_platform_download/remove?pcomp_version_platform_download=" + softwareDownloadId + "&pcomp_version=" + versionId + "&pcomp_software=" + softwareId,
            async: false,
            success: function (response) {
                if (!response.data) {
                    alert(response.message)
                } else {
                    fn();
                }
            }
        });
        return a;
    },
    deleteDocumentDownload: function (softwareId, versionId, documentDownloadId, fn) {
        var a = "";
        $.ajax({
            url: getContextPath() + "/pcomp/pcomp_version_description_document/remove?pcomp_version_description_document=" + documentDownloadId + "&pcomp_version=" + versionId + "&pcomp_software=" + softwareId,
            async: false,
            success: function (response) {
                if (!response.data) {
                    alert(response.message)
                } else {
                    fn();
                }
            }
        });
        return a;
    },
    newSoftwareDownload: function (versionId, platform, fn) {
        $.ajaxFileUpload
        (
            {
                url: getContextPath() + "/pcomp/pcomp_version_platform_download/create",
                type: 'post',
                data: {pcomp_version: versionId, pcomp_version_platform_download_platform: platform},
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
        );
        return false;
    },
    newDocumentDownload: function (versionId, description, fn) {
        $.ajaxFileUpload
        (
            {
                url: getContextPath() + "/pcomp/pcomp_version_description_document/create",
                type: 'post',
                data: {pcomp_version: versionId, pcomp_version_document_download_description: description},
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
        );
        return false;
    },
    modifySoftwareDownload: function (versionId, softwareDownloadId, platform, fn) {
        $.ajaxFileUpload
        (
            {
                url: getContextPath() + "/pcomp/pcomp_version_platform_download/update",
                type: 'post',
                data: {
                    pcomp_version: versionId,
                    pcomp_version_platform_download: softwareDownloadId,
                    pcomp_version_platform_download_platform: platform
                },
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
        );
        return false;
    },
    modifyDocumentDownload: function (versionId, documentId, description, fn) {
        $.ajaxFileUpload
        (
            {
                url: getContextPath() + "/pcomp/pcomp_version_description_document/update",
                type: 'post',
                data: {
                    pcomp_version: versionId,
                    pcomp_version_description_document: documentId,
                    pcomp_version_document_download_description: description
                },
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
        );
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
    delete: function (pcompKindId, pcompSoftwareId) {
        var a = "";
        $.ajax({
            url: getContextPath() + "/pcomp/pcomp_software/remove?pcomp_software=" + pcompSoftwareId,
            async: false,
            success: function (response) {
                if (!response.data) {
                    alert(response.message);
                } else {
                    window.location.href = getContextPath() + "/html/pcompKind.html?pcompKindId=" + pcompKindId;
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
                if (!isNull(response.message)) {
                    alert(response.message);
                    return;
                } else {
                    a = response.data;
                }
            }
        });
        return a;
    },
    getByTitleName: function (titleName) {
        var result;
        $.ajax({
            url: getContextPath() + "/pcomp/pcomp_kind/index/fetch?pcomp_title_title_name=" + titleName,
            async: false,
            success: function (response) {
                if (!isNull(response.message)) {
                    alert(response.message);
                    return;
                } else {
                    result = response.data;
                }
            }
        });
        return result;
    }
};


