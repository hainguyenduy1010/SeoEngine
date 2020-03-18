import api from "@/pages/backend-api.js"

export default {
    data() {
        return {
            fields: [{
                key: 'id',
                class: 'text-center',
                sortable: true
            }, {
                key: 'keyword',
                sortable: true
            }, {
                key: 'title',
                sortable: true,
            }, {
                key: 'description',
                sortable: true
            }, {
                key: 'create_date',
                sortable: true
            }, {
                key: 'update_date',
                sortable: true,
                class: 'text-center'
            // },{
            //     key: 'selected',
            //     class: 'text-center'
            },{
                key: 'actions',
                class: 'text-center'
            }],
            items: [],
            keywords: [],
            totalRows: 1,
            currentPage: 1,
            perPage: 20,
            pageOptions: [20, 50, 100],
            filter: null,
            filterOn: [],
            sortBy: 'id',
            sortDesc: false,
            selectedData: [],
            selectedIds: [],
            isDisableUpdate: true
        }
    },
    beforeMount() {
        if (this.$route.params.filter) {
            this.filter = this.$route.params.filter;
        }

        var type = this.filter ? 'count-keyword-like-keyword' : 'count-keyword-all'
        api.getCount(type, this.filter).then(response => {
            this.totalRows = response.data
        }, error => console.log(error));

        api.getKeywordDataList(this.currentPage, this.perPage, this.sortBy, this.sortDesc, this.filter).then(response => {
            this.items = response.data
        }, error => console.log(error));
    },
    methods: {
        onDetails(item) {
            this.$router.push({name: 'search-data-list', params: {keyword: item.keyword}});
        }, 
        onUpdate(item) {
            this.$router.push({name: 'update', params: {isKeywordAction: true, data: item}});
        }, 
        onRowSelected(items) {
            this.selectedData = items;
            this.selectedIds = items.map(m => m.id);
        },
        onSortChanged(ctx) {
            this.currentPage = 1;
            this.sortBy = ctx.sortBy;
            this.sortDesc = ctx.sortDesc;
            api.getKeywordDataList(1, this.perPage, ctx.sortBy, ctx.sortDesc, this.filter).then(response => {
                this.items = response.data
            }, error => console.log(error));
        },
        onPerPageChanged(perPage) {
            api.getKeywordDataList(1, perPage, this.sortBy, this.sortDesc, this.filter).then(response => {
                this.items = response.data
            }, error => console.log(error));
        },
        onCurrentPageChanged(currentPage) {
            api.getKeywordDataList(currentPage, this.perPage, this.sortBy, this.sortDesc, this.filter).then(response => {
                this.items = response.data
            }, error => console.log(error));
        },
        onFilterChanged(filter) {
            api.getKeywordList('keyword-list-kd', filter).then(response => {
                this.keywords = response.data
            }, error => console.log(error));
        },
        filterKeyword() {
            this.currentPage = 1;

            api.getCount('count-keyword-like-keyword', this.filter).then(response => {
                this.totalRows = response.data
            }, error => console.log(error));

            api.getKeywordDataList(this.currentPage, this.perPage, this.sortBy, this.sortDesc, this.filter).then(response => {
                this.items = response.data
            }, error => console.log(error));
        },
        clearFilter() {
            this.filter = '';
            api.getCount('count-keyword-all').then(response => {
                this.totalRows = response.data
            }, error => console.log(error));

            api.getKeywordDataList(this.currentPage, this.perPage, this.sortBy, this.sortDesc).then(response => {
                this.items = response.data
            }, error => console.log(error));
        },
        clearSelected() {
            this.$refs.table.clearSelected();
        },
        deleteRows() {
            api.deleteKeyword(this.selectedIds).then(response => {
                console.log(response.data);
                api.getCount('count-keyword-all').then(response => {
                    this.totalRows = response.data
                }, error => console.log(error));
    
                this.filter = '';
                api.getKeywordDataList(this.currentPage, this.perPage, this.sortBy, this.sortDesc).then(response => {
                    this.items = response.data
                }, error => console.log(error));
            }, error => console.log(error));
        }
    }
}