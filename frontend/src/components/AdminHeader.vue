<template>
    <b-navbar toggleable="lg" type="dark" class="header-bar" v-if="$route.path !== '/admin/login'">
        <a href="/admin">
            <img src="@/assets/search-logo.png">
        </a>

        <b-collapse id="nav-collapse" is-nav>
            <!-- Right aligned nav items -->
            <b-navbar-nav class="ml-auto">
                <form @submit.prevent="callLogout">
                    <b-button size="sm" class="my-2 my-sm-0" type="submit" variant="dark">Logout</b-button>
                </form>
            </b-navbar-nav>
        </b-collapse>
    </b-navbar>
</template>

<style scoped>
.header-bar {
    height: 57px;
    background-color: #f2f2f2;
    border-bottom: 1px solid #e4e4e4;
}
img {
    width: 150px;
}
</style>

<script>
export default {
	methods: {
		callLogout() {
            this.$router.push('/admin/login')
		}
	}
}
</script>