export default {
	name: 'login',
	data () {
		return {
			username: '',
			password: '',
			isSubmitted: false,
			error: false,
			errors: [],
			logo: require('@/assets/search-logo.png'),
		}
	},
	created () {
		// reset login status
		this.$store.dispatch("logout", this.username);
	},
	methods: {
		callLogin() {
			this.isSubmitted = true;
			this.errors = [];

			if (this.username && this.password) {
				this.$store.dispatch("login", {username: this.username, password: this.password}).then(() => {
					this.$router.push('/admin')
				}).catch(e => {
					this.errors.push(e);
					this.error = true;
				})
			}
		}
	}
}