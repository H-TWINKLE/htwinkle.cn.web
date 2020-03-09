$(document).ready(function () {
    if (TipMsg != null && TipMsg != "") {
        $.toast({
            heading: '消息提示',
            text: TipMsg,
            showHideTransition: 'slide',
            position: 'bottom-right',
            icon: 'info',
            hideAfter: 4000,
        })
    }
})