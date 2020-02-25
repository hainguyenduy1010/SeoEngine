import axios from 'axios'
import $ from 'jquery'

const AXIOS = axios.create({
	baseUrl: `/api`,
	headers: {'Content-Type': 'text/plain'},
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
	}
}