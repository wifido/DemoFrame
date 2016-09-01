<script>
    function isNull(str) {
        if (str == null || str == undefined || str == '') {
            return true;
        } else {
            var regu = "^[ ]+$";
            var re = new RegExp(regu);
            return re.test(str);
        }
    }

</script>