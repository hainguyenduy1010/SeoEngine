<template>
    <div class="data-list-table">
        <div class="row">
            <b-col lg="4" class="my-1">
                <b-form-group
                    label="Filter"
                    label-cols-sm="2"
                    label-align-sm="right"
                    label-size="sm"
                    label-for="filterInput"
                    class="mb-0"
                >
                    <b-input-group size="sm">
                        <b-form-input
                            v-model="filter"
                            type="search"
                            id="filterInput"
                            list="keywords"
                            placeholder="Type to Search"
                            @input="onFilterChanged"
                        ></b-form-input>
                        <datalist id="keywords">
                            <option>Keyword list</option>
                            <option v-for="(keyword, index) in keywords" v-bind:key="index">{{ keyword }}</option>
                        </datalist>

                        <b-input-group-append>
                            <b-button size="sm" class="ml-2" :disabled="!filter" variant="primary" @click="filterKeyword">Search</b-button>
                        </b-input-group-append>
                        <b-input-group-append>
                            <b-button size="sm" class="ml-2" :disabled="!filter" @click="clearFilter">Clear</b-button>
                        </b-input-group-append>
                    </b-input-group>
                </b-form-group>
            </b-col>

            <b-col class="my-1 text-right">
                <router-link :to="{name: 'create', params: {isKeywordAction: true}}">
                    <b-button size="sm" class="ml-2" variant="primary">Create</b-button>
                </router-link>
                <!-- <router-link :to="{name: 'update', params: {data: selectedData}}">
                    <b-button size="sm" class="ml-2" variant="info" :disabled="selectedIds.length !== 1">Update</b-button>
                </router-link> -->
                <b-button size="sm" class="ml-2" variant="danger" v-b-modal.del-confirm-modal :disabled="!selectedIds.length">Delete</b-button>
                <b-button size="sm" class="ml-2" @click="clearSelected" :disabled="!selectedIds.length">Clear selected</b-button>
                
                <b-modal id="del-confirm-modal" title="Delete" @ok="deleteRows">
                    <p class="my-4">Are you sure that you want to delete selected items?</p>
                </b-modal>
            </b-col>
        </div>

        <b-table
            ref="table"
            sticky-header="100%"
            sort-icon-left
            small hover no-border-collapse
            selectable
            select-mode="range"
            :items="items"
            :fields="fields"
            :sort-by.sync="sortBy"
            :sort-desc.sync="sortDesc"
            :current-page="1"
            :per-page="perPage"
            @row-selected="onRowSelected"
            @sort-changed="onSortChanged"
            >

            <template v-slot:cell(create_date)="data">
                <span>{{data.value | formatDate}}</span>
            </template>

            <template v-slot:cell(update_date)="data">
                <span>{{data.value | formatDate}}</span>
            </template>

            <template v-slot:cell(actions)="data">
                <b-button size="sm" variant="info" @click="onDetails(data.item)" class="m-1">
                    Details
                </b-button>
                <b-button size="sm" variant="info" @click="onUpdate(data.item)" class="m-1">
                    Update
                </b-button>
            </template>
        </b-table>

        <div class="row ">
            <b-col class="my-auto text-left">
                Total: {{totalRows}}
            </b-col>

            <b-col md="1.5" class="my-1">
                <b-form-group
                    label="Per page"
                    label-cols-lg="14"
                    label-align-sm="right"
                    label-size="sm"
                    label-for="perPageSelect"
                    class="mb-0"
                    >
                    <b-form-select
                        v-model="perPage"
                        id="perPageSelect"
                        size="sm"
                        :options="pageOptions"
                        v-on:change="onPerPageChanged"
                        >
                    </b-form-select>
                </b-form-group>
            </b-col>

            <b-col md="4" class="my-1">
                <b-pagination
                    v-model="currentPage"
                    :total-rows="totalRows"
                    :per-page="perPage"
                    align="fill"
                    limit="10"
                    size="sm"
                    class="my-0"
                    v-on:change="onCurrentPageChanged"
                    >
                </b-pagination>
            </b-col>
        </div>
    </div>
</template>

<style scoped>
.data-list-table {
    margin-left: 15px;
    margin-right: 15px;
    height: 78vh;
}
</style>

<script src='./keyword-list.js'></script>