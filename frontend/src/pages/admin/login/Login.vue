<template>
	<div class="login-wapper">
		<div class="login-container">
			<a href="/admin">
				<img :src="logo">
			</a>
			<form @submit.prevent="callLogin">
				<div class="login-error-msg" v-if="error">Username or password is incorrect</div>
				<div class="form-group">
					<label for="username">Username</label>
					<input type="text" v-model="username" name="username" class="form-control" :class="{ 'is-invalid': isSubmitted && !username }" />
					<div v-show="isSubmitted && !username" class="invalid-feedback">Username is required</div>
				</div>
				<div class="form-group">
					<label htmlFor="password">Password</label>
					<input type="password" v-model="password" name="password" class="form-control" :class="{ 'is-invalid': isSubmitted && !password }" />
					<div v-show="isSubmitted && !password" class="invalid-feedback">Password is required</div>
				</div>
				<div class="form-group submit-btn">
					<button class="btn btn-primary">Login</button>
				</div>
			</form>
		</div>
	</div>
</template>

<style scoped>
	@import './login.css';
</style> 

<script src="./login.js"></script>