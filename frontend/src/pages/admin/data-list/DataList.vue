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
                            placeholder="Type to Search"
                        ></b-form-input>
                        <b-input-group-append>
                            <b-button :disabled="!filter" @click="filter = ''">Clear</b-button>
                        </b-input-group-append>
                    </b-input-group>
                </b-form-group>
            </b-col>

            <b-col class="my-1 text-right">
                <b-button size="sm" class="ml-2" @click="clearSelected">Clear selected</b-button>
                <b-button size="sm" class="ml-2" variant="danger" @click="deleteRows">Delete</b-button>
            </b-col>
        </div>

        <b-table
            ref="selectableTable"
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
            :filter="filter"
            :filterIncludedFields="filterOn"
            @filtered="onFiltered"
            @row-selected="onRowSelected"
            >

            <template v-slot:cell(url)="data">
                <a :href="`${data.value}`" target="_blank">{{ data.value }}</a>
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

        <div class="row float-right" style="padding: 0 1rem;">
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
                        v-on:change="onChangePerPage"
                        >
                    </b-form-select>
                </b-form-group>
            </b-col>
            <b-col sm="7" md="6" class="my-1">
                <b-pagination
                    v-model="currentPage"
                    :total-rows="totalRows"
                    :per-page="perPage"
                    align="fill"
                    size="sm"
                    class="my-0"
                    v-on:change="onChangeCurrentPage"
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