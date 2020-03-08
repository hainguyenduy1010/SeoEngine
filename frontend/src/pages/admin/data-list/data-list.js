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
            },{
                key: 'selected',
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
            sortBy: 'keyword',
            sortDesc: false,
            selectedIds: [],
            isDisableUpdate: true
        }
    },
    beforeMount() {
        if (this.$route.params.filter) {
            this.filter = this.$route.params.filter;
        }

        api.getCount(this.filter).then(response => {
            this.totalRows = response.data
        }, error => console.log(error));

        api.getDataList(this.currentPage, this.perPage, this.sortBy, this.sortDesc, this.filter).then(response => {
            this.items = response.data
        }, error => console.log(error));
    },
    methods: {
        onRowSelected(items) {
            this.selectedIds = items.map(m => m.id);

            this.isDisableUpdate = false;
            var keywords = items.map(m => m.keyword);
            for (var i = 0; i < keywords.length - 1; i++) {
                if (keywords[i] !== keywords[i + 1]) {
                    this.isDisableUpdate = true;
                    break;
                }
            }
        },
        onSortChanged(ctx) {
            this.currentPage = 1;
            this.sortBy = ctx.sortBy;
            this.sortDesc = ctx.sortDesc;
            api.getDataList(1, this.perPage, ctx.sortBy, ctx.sortDesc, this.filter).then(response => {
                this.items = response.data
            }, error => console.log(error));
        },
        onPerPageChanged(perPage) {
            api.getDataList(1, perPage, this.sortBy, this.sortDesc, this.filter).then(response => {
                this.items = response.data
            }, error => console.log(error));
        },
        onCurrentPageChanged(currentPage) {
            api.getDataList(currentPage, this.perPage, this.sortBy, this.sortDesc, this.filter).then(response => {
                this.items = response.data
            }, error => console.log(error));
        },
        onFilterChanged(filter) {
            api.getKeywordList(filter).then(response => {
                this.keywords = response.data
            }, error => console.log(error));
        },
        filterKeyword() {
            this.currentPage = 1;

            api.getCount(this.filter).then(response => {
                this.totalRows = response.data
            }, error => console.log(error));

            api.getDataList(this.currentPage, this.perPage, this.sortBy, this.sortDesc, this.filter).then(response => {
                this.items = response.data
            }, error => console.log(error));
        },
        clearFilter() {
            this.filter = '';
            api.getCount().then(response => {
                this.totalRows = response.data
            }, error => console.log(error));

            api.getDataList(this.currentPage, this.perPage, this.sortBy, this.sortDesc).then(response => {
                this.items = response.data
            }, error => console.log(error));
        },
        clearSelected() {
            this.$refs.table.clearSelected();
        },
        deleteRows() {
            api.delete(this.selectedIds).then(response => {
                console.log(response.data);
                api.getCount().then(response => {
                    this.totalRows = response.data
                }, error => console.log(error));
    
                this.filter = '';
                api.getDataList(this.currentPage, this.perPage, this.sortBy, this.sortDesc).then(response => {
                    this.items = response.data
                }, error => console.log(error));
            }, error => console.log(error));
        }
    }
}