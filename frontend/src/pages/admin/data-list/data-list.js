import api from "@/pages/backend-api.js"

export default {
    data() {
        return {
            fields: [{
                key: 'id',
                class: 'text-center',
                sortable: true
            }, {
                key: 'url',
                label: "URL",
                sortable: true
            }, {
                key: 'order',
                sortable: true,
                class: 'text-center'
            }, {
                key: 'create_date',
                sortable: true,
                class: 'text-center'
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
            totalRows: 0,
            currentPage: 1,
            perPage: 20,
            pageOptions: [20, 50, 100],
            filter: null,
            filterOn: [],
            sortBy: 'order',
            sortDesc: false,
            selectedData: [],
            selectedIds: [],
            isDisableUpdate: true,
            keyword: ''
        }
    },
    beforeMount() {
        if (this.$route.params.filter) {
            this.filter = this.$route.params.filter;
        }
        
        if (this.$route.params.keyword) {
            this.keyword = this.$route.params.keyword;
        }
        
        var type = this.filter ? 'count-search-data-like-url' : 'count-search-data-all'
        api.getCount(type, this.filter, this.keyword).then(response => {
            this.totalRows = response.data
        }, error => console.log(error));

        api.getSearchDataList(this.keyword, this.currentPage, this.perPage, this.sortBy, this.sortDesc, this.filter).then(response => {
            this.items = response.data
        }, error => console.log(error));
    },
    methods: { 
        onUpdate(item) {
            this.$router.push({name: 'update', params: {data: item}});
        }, 
        onRowSelected(items) {
            this.selectedData = items;
            this.selectedIds = items.map(m => m.id);

            // this.isDisableUpdate = false;
            // var keywords = items.map(m => m.keyword);
            // for (var i = 0; i < keywords.length - 1; i++) {
            //     if (keywords[i] !== keywords[i + 1]) {
            //         this.isDisableUpdate = true;
            //         break;
            //     }
            // }
        },
        onSortChanged(ctx) {
            this.currentPage = 1;
            this.sortBy = ctx.sortBy;
            this.sortDesc = ctx.sortDesc;
            api.getSearchDataList(this.keyword, 1, this.perPage, ctx.sortBy, ctx.sortDesc, this.filter).then(response => {
                this.items = response.data
            }, error => console.log(error));
        },
        onPerPageChanged(perPage) {
            api.getSearchDataList(this.keyword, 1, perPage, this.sortBy, this.sortDesc, this.filter).then(response => {
                this.items = response.data
            }, error => console.log(error));
        },
        onCurrentPageChanged(currentPage) {
            api.getSearchDataList(this.keyword, currentPage, this.perPage, this.sortBy, this.sortDesc, this.filter).then(response => {
                this.items = response.data
            }, error => console.log(error));
        },
        onFilterChanged(filter) {
            api.getKeywordList('keyword-list-sd', filter).then(response => {
                this.keywords = response.data
            }, error => console.log(error));
        },
        filterKeyword() {
            this.currentPage = 1;

            api.getCount('count-search-data-like-url', this.filter, this.keyword).then(response => {
                this.totalRows = response.data
            }, error => console.log(error));

            api.getSearchDataList(this.keyword, this.currentPage, this.perPage, this.sortBy, this.sortDesc, this.filter).then(response => {
                this.items = response.data
            }, error => console.log(error));
        },
        clearFilter() {
            this.filter = '';
            api.getCount('count-search-data-all', null, this.keyword).then(response => {
                this.totalRows = response.data
            }, error => console.log(error));

            api.getSearchDataList(this.keyword, this.currentPage, this.perPage, this.sortBy, this.sortDesc).then(response => {
                this.items = response.data
            }, error => console.log(error));
        },
        clearSelected() {
            this.$refs.table.clearSelected();
        },
        deleteRows() {
            api.delete(this.selectedIds).then(response => {
                console.log(response.data);
                api.getCount('count-search-data-all', null, this.keyword).then(response => {
                    this.totalRows = response.data
                }, error => console.log(error));
    
                this.filter = '';
                api.getSearchDataList(this.keyword, this.currentPage, this.perPage, this.sortBy, this.sortDesc).then(response => {
                    this.items = response.data
                }, error => console.log(error));
            }, error => console.log(error));
        }
    }
}