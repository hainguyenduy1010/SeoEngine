<template>
    <b-container fluid="md" class="create-form">
        <b-form @submit.stop.prevent="onSubmit">
            <b-row class="header-row">
                <b-col align-v="right">
                    <router-link to="/admin">
                        <b-button class="mt-3 mb-1">Back</b-button>
                    </router-link>
                </b-col>
                <b-col class="text-right" align-v="right">
                    <b-button class="mt-3" type="submit" variant="primary" :disabled="!isShowForm">Save</b-button>
                    <div>
                        <b-modal ref="create-modal" title="Create search data" ok-only @ok="$router.push({name: 'admin', params: {filter: keyword}})">
                            {{rows.length}} have been created.
                        </b-modal>
                    </div>
                </b-col>
            </b-row>
            <b-row class="keyword-row" align-v="center">
                <b-col cols="1" class="title-col"><b>Keyword</b></b-col>
                <b-col>
                    <b-input class="keyword-input" v-model="keyword"  @input="validateKeyword" @blur="validateKeyword(keyword)"></b-input>
                </b-col>
                <b-col cols="1" class="text-center">
                    <b-button class="keyword-ok-btn" size="sm" variant="success" @click="onKeywordOk">OK</b-button>
                </b-col>
            </b-row>
            <b-row class="keyword-invalid-feedback-div">
                <b-col cols="1"></b-col>
                <b-col>
					<div class="invalid-feedback keyword-invalid-feedback">Keyword is required.</div>
                </b-col>
            </b-row>

            <div v-show="isShowForm">
                <b-row class="mt-4" align-v="center">
                    <b-col cols="1"><b>No.</b></b-col>
                    <b-col><b>URL</b></b-col>
                    <b-col cols="2"><b>Order</b></b-col>
                </b-row>
                
                <div v-for="(row, index) in rows" v-bind:key="index">
                    <b-row align-v="center">
                        <b-col cols="1" class="text-center">
                            <span>{{index + 1}}</span>
                        </b-col>
                        <b-col>
                            <b-input :id="'url-input-'+index" class="url-input" v-model="row.url" @input="validateURL(row.url, {index})" @blur="validateURL(row.url, {index})"></b-input>
                        </b-col>
                        <b-col cols="1.5">
                            <vue-numeric-input class="number-input" v-model="row.order" :min="1" :max="order_init + index"
                            align="center" size="100px" controls-type="updown" @change="validateOrder(row.order, {index})"></vue-numeric-input>
                        </b-col>
                        <b-col cols="1" class="text-center">
                            <b-button class="remove-btn" size="sm" variant="danger" v-show="index > 0" @click="onRemove({index})">-</b-button>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col cols="1">
                        </b-col>
                        <b-col>
                            <div :id="'invalid-feedback-'+index" class="invalid-feedback">URL is required and should be correct.</div>
                        </b-col>
                    </b-row>
                </div>
                <b-row class="mt-3">
                    <b-col cols="1" class="text-center">
                        <b-button size="sm" class="add-btn mb-3" variant="success" @click="onAdd">+</b-button>
                    </b-col>
                </b-row>
            </div>
        </b-form>
    </b-container>
</template>

<style scoped>
    @import'./create.css';
</style>

<script src='./create.js'></script>