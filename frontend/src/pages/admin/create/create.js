import $ from 'jquery'
import api from '@/pages/backend-api.js'

export default {
    data() {
        return {
            keyword: '',
            title: '',
            description: '',
            isShowForm: false,
            order_init: 0,
            order_latest: 0,
            rows: [{
                url: '',
                order: this.order_init
            }],
            isKeywordAction: false,
            isUpdate: false,
            order_max: null,
            successMsg: ''
        }
    },
    beforeMount() {
        if (this.$route.name === 'create'){
            if (this.$route.params.isKeywordAction) {
                this.isKeywordAction = true;
            } else {
                if (this.$route.params.keyword) {
                    this.keyword = this.$route.params.keyword;
                }
                
                api.getLatestOrder(this.keyword).then(response => {
                    this.order_init = response.data + 1;
                    this.order_latest = this.order_init;
                    this.rows[0].order = this.order_init;
                }, error => console.log(error));
            }
        }

        if (this.$route.name === 'update') {
            this.isUpdate = true;
            this.keyword = this.$route.params.data.keyword;
            this.title = this.$route.params.data.title;
            this.description = this.$route.params.data.description;

            if (this.$route.params.isKeywordAction) {
                this.isKeywordAction = true;
            } else {
                this.rows[0] = this.$route.params.data;
                api.getLatestOrder(this.keyword).then(response => {
                    this.order_max = response.data;
                }, error => console.log(error));
            }
        }
    },

    methods: {
        onSubmit() {
            if (this.validateKeyword($('.keyword-input').val())) {
                
                var isInvalid = false;
                if (!this.isKeywordAction) {
                    this.rows.forEach((row, index) => {
                        var indexObj = {index: index};
                        if (!this.validateURL(row.url, indexObj)) {
                            isInvalid = true;
                            return false;
                        }
                    });
                }
                
                if (!isInvalid) {
                    if (this.isKeywordAction) {
                        var id = this.$route.params.data ? this.$route.params.data.id : null;
                        api.createOrUpdateKeyword(id, this.keyword, this.title, this.description).then(response => {
                            console.log(response.data);
                            this.successMsg = response.data;
                            this.showModal();
                        }, error => console.log(error));
                        return
                    }

                    if (!this.isUpdate) {
                        api.create(this.keyword, this.rows).then(response => {
                            console.log(response.data);
                            this.successMsg = response.data;
                            this.showModal();
                        }, error => console.log(error));
                    } else {
                        this.rows[0].keyword = this.keyword;
                        api.update(this.rows).then(response => {
                            console.log(response.data);
                            this.successMsg = response.data;
                            this.showModal();
                        }, error => console.log(error));
                    }
                }
            }
        },
        // onKeywordOk() {
        //     if (this.validateKeyword($('.keyword-input').val())) {
        //         $('.keyword-input').prop('disabled', !this.isShowForm);
        //         $('.keyword-ok-btn').html(!this.isShowForm ? 'Edit' : 'OK');
        //         this.rows = [{
        //             url: '',
        //             order: this.order_init
        //         }];
        //         $('#url-input-0').removeClass('is-invalid');
        //         $('#invalid-feedback-0').css('display', 'none');

        //         this.isShowForm = !this.isShowForm;
                
        //         if (this.isShowForm) {
        //             api.getLatestOrder(this.keyword).then(response => {
        //                 this.order_init = response.data + 1;
        //                 this.order_latest = this.order_init;
        //                 this.rows[0].order = this.order_init;
        //             }, error => console.log(error));
        //         }
        //     }
        // },
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
                $('.keyword-input').addClass('is-invalid');
                $('.keyword-invalid-feedback').css('display', 'block');
                return false;
            } else {
                $('.keyword-input').removeClass('is-invalid');
                $('.keyword-invalid-feedback').css('display', 'none');
                return true;
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
            return pattern.test(url);
        },
        validateOrder(order, indexObj) {
            var index = indexObj.index;
            if (!order) {
                this.rows[index].order = this.order_init + index;
            }
        },
        showModal() {
            this.$refs['create-modal'].show()
        },
        hideModal() {
            this.$refs['create-modal'].hide()
        },
        onModalOk() {
            if (this.$route.params.isKeywordAction) {
                this.$router.push({name: 'admin', params: {filter: this.isKeywordAction ? null : this.keyword}})
            } else {
                this.$router.push({name: 'search-data-list', params: {keyword : this.keyword}})
            }
        },
        setOrderMax(indexObj) {
            $('.numeric-input').prop('disabled', true);
            
            var index = indexObj.index;
            var max = this.isUpdate ? this.order_max : this.order_init + index;

            max = max == null ? 99999999999999 : max;

            return max;
        }
    }
}