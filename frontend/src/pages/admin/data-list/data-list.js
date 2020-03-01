import api from "@/pages/backend-api.js"

export default {
    data() {
        return {
            fields: [{
                key: 'selected',
                class: 'text-center'
            }, {
                key: 'keyword',
                sortable: true
            }, {
                key: 'url',
                label: "URL",
                sortable: true
            }, {
                key: 'sortkey',
                sortable: true,
                class: 'text-center'
            }],
            items: [],
            totalRows: 1,
            currentPage: 1,
            perPage: 20,
            pageOptions: [20, 50, 100],
            filter: null,
            filterOn: [],
            sortBy: 'keyword',
            sortDesc: false,
            selectedIds: []
        }
    },
    beforeMount() {
        api.getCount().then(response => {
            this.totalRows = response.data
        }, error => console.log(error));

        api.getDataList(1, 20).then(response => {
            this.items = response.data
        }, error => console.log(error));
    },
    methods: {
        onRowSelected(items) {
            this.selectedIds = items.map(m => m.id);
        },
        onFiltered() {
            this.currentPage = 1;
        },
        onChangePerPage(perPage) {
            api.getDataList(1, perPage).then(response => {
                this.items = response.data
            }, error => console.log(error));
        },
        onChangeCurrentPage(currentPage) {
            api.getDataList(currentPage, this.perPage).then(response => {
                this.items = response.data
            }, error => console.log(error));
        },
        clearSelected() {
          this.$refs.selectableTable.clearSelected();
        },
        deleteRows() {
            api.delete(this.selectedIds).then(response => {
                console.log(response.data);
                this.$refs.table.refresh()
            }, error => console.log(error));
        }
    }
}