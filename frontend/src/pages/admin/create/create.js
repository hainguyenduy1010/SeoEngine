import $ from 'jquery'

export default {
    data() {
        return {
            keyword: '',
            order_init: 46,
            order_latest: 46,
            rows: [{
                url: '',
                order: 46
            }]
        }
    },

    methods: {
        onSubmit() {
            alert("Form submitted!");
        },
        onAdd() {
            var index = $('.url-input').length - 1;
            var indexObj = {index: index};
            if (this.validateURL($('.url-input:last').val(), indexObj)) { //.hasClass('is-invalid')) {
                this.order_latest = this.order_latest + 1;
                this.rows.push({
                    url: '',
                    order: this.order_latest
                });
            }
        },
        onRemove(indexObj) {
            var index = indexObj.index;

            var removeOrder = this.rows[index].order;
            this.rows.forEach(row => {
                if (row.order > removeOrder) {
                    row.order = row.order - 1;
                }
            });

            this.rows.splice(index, 1);
        },
        validateKeyword(keyword) {
            if (!keyword) {
                console.log(keyword)
                $('.keyword-input').addClass('is-invalid');
                $('.keyword-invalid-feedback').css('display', 'block');
            } else {
                console.log(keyword)
                $('.keyword-input').removeClass('is-invalid');
                $('.keyword-invalid-feedback').css('display', 'none');
            }
        },
        validateURL(url, indexObj) {
            var index = indexObj.index;
            var pattern = new RegExp('^(https?:\\/\\/)?'+ // protocol
                '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|'+ // domain name
                '((\\d{1,3}\\.){3}\\d{1,3}))'+ // OR ip (v4) address
                '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*'+ // port and path
                '(\\?[;&a-z\\d%_.~+=-]*)?'+ // query string
                '(\\#[-a-z\\d_]*)?$','i'); // fragment locator
            
            if (!pattern.test(url)) {
                $('#url-input-'+index).addClass('is-invalid');
                $('#invalid-feedback-'+index).css('display', 'block');
            } else {
                $('#url-input-'+index).removeClass('is-invalid');
                $('#invalid-feedback-'+index).css('display', 'none');
            }
            return !!pattern.test(url);
        },
        validateOrder(order, indexObj) {
            var index = indexObj.index;
            if (!order) {
                this.rows[index].order = this.order_init + index;
            }
        }
    }
}