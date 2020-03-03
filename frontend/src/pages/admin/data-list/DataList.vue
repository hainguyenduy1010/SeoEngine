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
                <b-button size="sm" class="ml-2" variant="success">New</b-button>
                <b-button size="sm" class="ml-2" variant="info" :disabled="selectedIds.length !== 1">Update</b-button>
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
            <!-- :filter="filter"
            :filterIncludedFields="filterOn"
            @filtered="onFiltered" -->

            <template v-slot:cell(url)="data">
                <a :href="`${data.value}`" target="_blank">{{ data.value }}</a>
            </template>

            <template v-slot:cell(create_date)="data">
                <span>{{data.value | formatDate}}</span>
            </template>

            <template v-slot:cell(update_date)="data">
                <span>{{data.value | formatDate}}</span>
            </template>

            <template v-slot:cell(selected)="{ rowSelected }">
                <template v-if="rowSelected">
                    <span aria-hidden="true"><svg xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" width="20" height="20" viewBox="0 0 48 48" style=" fill:#000000;"><path fill="#c8e6c9" d="M36,42H12c-3.314,0-6-2.686-6-6V12c0-3.314,2.686-6,6-6h24c3.314,0,6,2.686,6,6v24C42,39.314,39.314,42,36,42z"></path><path fill="#4caf50" d="M34.585 14.586L21.014 28.172 15.413 22.584 12.587 25.416 21.019 33.828 37.415 17.414z"></path></svg></span>
                    <span class="sr-only">Selected</span>
                </template>
                <template v-else>
                    <span aria-hidden="true">&nbsp;</span>
                    <span class="sr-only">Not selected</span>
                </template>
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
    @import'./data-list.css'
</style>

<script src='./data-list.js'></script>