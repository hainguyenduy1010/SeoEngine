import axios from 'axios'
import $ from 'jquery'

const AXIOS = axios.create({
	baseUrl: `/api`,
	headers: {
		'Content-Type': 'application/json; charset=utf-8'
	}
	// timeout: 30000
});

export default {
	search(keyword) {
		return AXIOS.post(`/api/search`, keyword);
	},

	search2(keyword, current_page) {
		return $.ajax({
			method: "POST",
			url: "/api/search",
			async: false,
			headers: {
				'Content-Type': 'application/json; charset=utf-8',
			},
			data: JSON.stringify({
				keyword: keyword,
				current_page: current_page
			})
		});
	},

	login(username, password) {		
		return AXIOS.post(`/api/login`, JSON.stringify({
			username: username,
			password: password
		}));
	},

	getCount(filter) {		
		return AXIOS.post(`/api/count`, JSON.stringify({
			filter: filter
		}));
	},

	getDataList(current_page, per_page, sort_by, is_sort_desc, filter) {
		return AXIOS.post(`/api/data-list`, JSON.stringify({
			current_page: current_page,
			per_page: per_page,
			sort_by: sort_by,
			is_sort_desc: is_sort_desc,
			filter: filter
		}));
	},

	getKeywordList(filter) {
		return AXIOS.post(`/api/keyword-list`, JSON.stringify({
			filter: filter
		}));
	},

	delete(ids) {
		return AXIOS.post(`/api/delete`, ids);
	}
}