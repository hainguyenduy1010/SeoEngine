<template>
    <b-container fluid="md" class="create-form">
        <b-form>
            <b-row class="header-row">
                <b-col align-v="right">
                    <router-link :to="isKeywordAction ? '/admin' : {name: 'search-data-list', params: {keyword: keyword}}">
                        <b-button class="mt-3 mb-1">Back</b-button>
                    </router-link>
                </b-col>
                <b-col class="text-right" align-v="right">
                    <b-button class="mt-3" @click="onSubmit" variant="primary">{{isUpdate ? 'Update' : 'Create'}}</b-button>
                    <div>
                        <b-modal ref="create-modal" title="Create search data" ok-only @ok="onModalOk">
                            {{successMsg}}
                        </b-modal>
                    </div>
                </b-col>
            </b-row>
            <b-row class="keyword-row" align-v="center">
                <b-col cols="1" class="title-col"><b>Keyword</b></b-col>
                <b-col>
                    <b-input class="keyword-input" v-model="keyword" @input="validateKeyword" :disabled="!isKeywordAction" @blur="validateKeyword(keyword)"></b-input>
                </b-col>
                <!-- <b-col cols="1" class="text-center" v-show="!isUpdate">
                    <b-button class="keyword-ok-btn" size="sm" variant="success" @click="onKeywordOk">OK</b-button>
                </b-col> -->
            </b-row>
            <b-row class="keyword-invalid-feedback-div">
                <b-col cols="1"></b-col>
                <b-col>
					<div class="invalid-feedback keyword-invalid-feedback">Keyword is required.</div>
                </b-col>
            </b-row>

            <b-row class="keyword-row"  align-v="center" v-show="isKeywordAction">
                <b-col cols="1" class="title-col"><b>Title</b></b-col>
                <b-col>
                    <b-input class="title-input" v-model="title"></b-input>
                </b-col>
            </b-row>
            <b-row class="description-row"  align-v="center" v-show="isKeywordAction">
                <b-col cols="1" class="title-col"><b>Description</b></b-col>
                <b-col class="pb-3">
                    <b-form-textarea class="description-input" rows="10" :disabled="!isKeywordAction" no-resize v-model="description"></b-form-textarea>
                </b-col>
            </b-row>

            <!-- <div v-show="(isShowForm || isUpdate) && !isKeywordAction"> -->
            <div v-show="!isKeywordAction">
                <b-row class="mt-4" align-v="center">
                    <b-col cols="1">No</b-col>
                    <b-col>URL</b-col>
                    <!-- <b-col cols="2">Order</b-col> -->
                </b-row>
                
                <div v-for="(row, index) in rows" v-bind:key="index">
                    <b-row align-v="center" class="mt-2">
                        <b-col cols="1" class="text-center">
                            <span>{{index + 1}}</span>
                        </b-col>
                        <b-col>
                            <b-input :id="'url-input-'+index" class="url-input" v-model="row.url" @input="validateURL(row.url, {index})" @blur="validateURL(row.url, {index})"></b-input>
                        </b-col>
                        <b-col cols="1.5" v-show="isUpdate && !isKeywordAction">
                            <vue-numeric-input class="number-input" v-model="row.order" :min="1" :max="setOrderMax({index})"
                            align="center" size="100px" controls-type="updown" @change="validateOrder(row.order, {index})"></vue-numeric-input>
                        </b-col>
                        <b-col cols="1" class="text-center">
                            <b-button class="remove-btn" size="sm" variant="danger" v-show="index > 0 && !isUpdate" @click="onRemove({index})">-</b-button>
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
                <b-row class="mt-3" v-show="!isUpdate">
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