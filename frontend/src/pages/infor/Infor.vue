<template>
    <div>
        <b-container fluid="md" class="mb-5">
            <div class="infor-logo">
                <a href="/">
                    <img :src="require('@/assets/search-logo.png')">
                </a>
            </div>
            <div class="text-justify">
                <b-tabs content-class="mt-3" fill v-model="tabIndex">
                    <b-tab title="About us">
                        <div v-html="content" :title-link-class="onTitleClick()"></div>
                    </b-tab>
                    <b-tab title="Copyright">
                        <div v-html="content" :title-link-class="onTitleClick()"></div>
                    </b-tab>
                    <b-tab title="Disclaimer">
                        <div v-html="content" :title-link-class="onTitleClick()"></div>
                    </b-tab>
                    <b-tab title="Privacy policy">
                        <div v-html="content" :title-link-class="onTitleClick()"></div>
                    </b-tab>
                    <b-tab title="End user license agreement">
                        <div v-html="content" :title-link-class="onTitleClick()"></div>
                    </b-tab>
                    <!-- <b-tab title="Contact us">
                        <div v-html="content" :title-link-class="onTitleClick()"></div>
                    </b-tab> -->
                </b-tabs>
            </div>
        </b-container>
    <Footer style="position: unset;"></Footer>
    </div>
</template>

<style scoped>
.infor-logo {
    text-align: center;
    margin-bottom: 20px
}
.infor-logo img {
    width: 300px;
}
</style>

<script>
import Footer from '@/components/Footer.vue'
export default {
	name: 'infor',
	components: {
		Footer
	},
	data() {
        return {
            tabIndex: 0,
            content: ''
        }
    },
	beforeMount() {
        const urlParams = new URLSearchParams(window.location.search);
        const indexQuery = urlParams.get('i');
        if (indexQuery) {
            this.tabIndex = parseInt(indexQuery);
            this.onTitleClick();
        }
	},
    methods: {
        onTitleClick() {
            switch (this.tabIndex) {
                case 0:
                    this.content = require('@/assets/content/about-us.txt').default
                    break;
                case 1:
                    this.content = require('@/assets/content/copyright.txt').default
                    break;
                case 2:
                    this.content = require('@/assets/content/disclaimer.txt').default
                    break;
                case 3:
                    this.content = require('@/assets/content/privacy-policy.txt').default
                    break;
                case 4:
                    this.content = require('@/assets/content/end-user-license-agreement.txt').default
                    break;
                // case 5:
                //     this.content = require('@/assets/content/contact-us.txt').default
                //     break;
                default:
                    break;
            }
        }
    }
}
</script>