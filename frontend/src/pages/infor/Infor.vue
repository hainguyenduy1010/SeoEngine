<template>
    <b-container fluid="md">
        <div class="infor-logo">
            <a href="/">
                <img :src="require('@/assets/search-logo.png')">
            </a>
        </div>
        <div>
        <b-card no-body>
            <b-tabs pills card vertical>
                <b-tab title="Tab 1" active>
                    <b-card-text>Tab contents 1</b-card-text>
                </b-tab>
                <b-tab title="Tab 2">
                    <b-card-text>Tab contents 2</b-card-text>
                </b-tab>
                <b-tab title="Tab 3">
                    <b-card-text>Tab contents 3</b-card-text>
                </b-tab>
            </b-tabs>
        </b-card>
        <b-tabs content-class="mt-3" align="center"    vertical>
    <b-tab title="First" active><p>I'm the first tab</p></b-tab>
    <b-tab title="Second"><p>I'm the second tab</p></b-tab>
    <b-tab title="Disabled" disabled><p>I'm a disabled tab!</p></b-tab>
  </b-tabs>
        </div>
    </b-container>
</template>

<style scoped>
.infor-logo {
    text-align: center
}
.infor-logo img {
    width: 300px;
}
</style>

<script>
export default {
	name: 'infor',
	mounted() {
        console.log("asdsad")
    }
}
</script>