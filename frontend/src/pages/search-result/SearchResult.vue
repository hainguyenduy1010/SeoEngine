<template>
	<div id="app">
		<div class="body page-result">
			<header id="header" class="navbar-fixed-top">
				<div class="header-container">
					<div class="logo search-header">
						<a href="/">
							<img :src="logo">
						</a>
					</div>

					<div id="search-box">
						<form @submit.prevent="searchSubmit">
							<input type="text" id="search-input" class="form-control" placeholder="Search thousands of coupons and stores" v-model="keyword">
							<!-- <router-link :to="{name: 'search', params: {keyword}}"> -->
								<!-- <a v-bind:href="'/search?k=' + keyword"> -->
									<button class="btn-search" type="submit" title="Searching...">
										<svg x="0px" y="0px" viewBox="0 0 32 32" style="enable-background:new 0 0 32 32;" xml:space="preserve">
											<g id="Page-1">
												<g id="icon-111-search">
													<path class="searchIcon" d="M19.9,22.7c-2,1.4-4.4,2.2-7,2.2C6.1,25,0.7,19.5,0.7,12.8S6.1,0.7,12.8,0.7S25,6.1,25,12.8
													c0,2.6-0.8,5-2.2,7l8,8c0.8,0.8,0.8,2,0,2.8l0,0c-0.8,0.8-2,0.8-2.8,0L19.9,22.7L19.9,22.7z M12.8,22.1c5.1,0,9.3-4.2,9.3-9.3
													S18,3.6,12.8,3.6s-9.3,4.2-9.3,9.3S7.7,22.1,12.8,22.1L12.8,22.1z"></path>
												</g>
											</g>
										</svg>
									</button>
								<!-- </a> -->
							<!-- </router-link> -->
						</form>
					</div>
					<div class="clear"></div>
				</div>
			</header>
			
			<div id="search-result-wapper">
				<div class="result-container">
					<div class="filter-wrapper">
						<div class="search-counter-about">
							<span>About </span>
							<span><b>{{result_count_fake}}</b></span>
							<span> results </span>
							<span class="total-time"> ({{search_result.total_time}} milliseconds) </span>
						</div>
						<div class="filter-content">

						</div>
					</div>

					<div class="search-result-wrapper">
						<div class="search-result-list">
							<!-- loop through result list -->
							<div class="search-result" v-for="search_data in search_result.search_data_list" v-if="search_data" v-bind:key="search_data.id">
								<h3>
									<a target="_blank" v-bind:href="search_data.url">
										<span v-html="search_data.title"></span>
									</a>
								</h3>
								<a class="search-link" target="_blank" v-bind:href="search_data.url">
									{{search_data.url}}
								</a>
								<p v-html="search_data.description"></p>
							</div>
							<!-- end loop -->
						</div>
						<div class="suggestion-search">
							<div class="suggestion-content">
								<h4 v-if="search_result.suggestion_list">Related searches</h4>
								<ul class="suggestion-key">
									<li v-for="(suggestion, index) in search_result.suggestion_list" v-bind:key="index">
										<a v-bind:href="suggestion.path">
											<span v-html="suggestion.suggestion_keyword"></span>
										</a>
									</li>
								</ul>
							</div>
						</div>
					</div>

					<div class="page-navbar">
						<b-pagination
							v-model="current_page"
							:total-rows="search_result.result_count"
							align="fill">
						</b-pagination>
					</div>
				</div>
			</div>
		</div>

		<Footer style="position: unset;"></Footer>
	</div>
</template>

<style>
	@import '../../assets/styles/page-result.css';
</style>

<script src="./search-result.js"></script>