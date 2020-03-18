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

	getCount(type, filter, keyword) {		
		return AXIOS.post(`/api/count`, JSON.stringify({
			type: type,
			filter: filter,
			keyword: keyword
		}));
	},

	getKeywordDataList(current_page, per_page, sort_by, is_sort_desc, filter) {
		return AXIOS.post(`/api/list/keyword-data`, JSON.stringify({
			current_page: current_page,
			per_page: per_page,
			sort_by: sort_by,
			is_sort_desc: is_sort_desc,
			filter: filter
		}));
	},

	getKeywordList(type, filter) {
		return AXIOS.post(`/api/keyword-list`, JSON.stringify({
			type: type,
			filter: filter
		}));
	},

	getLatestOrder(keyword) {
		return AXIOS.get(`/api/get-latest-order/` + keyword);
	},

	createOrUpdateKeyword(id, keyword, title, description) {
		return AXIOS.post(`/api/create-update/keyword`, JSON.stringify({
			id: id,
			keyword: keyword,
			title: title,
			description: description
		}));
	},

	deleteKeyword(ids) {
		return AXIOS.post(`/api/delete/keyword`, ids);
	},

	getSearchDataList(keyword, current_page, per_page, sort_by, is_sort_desc, filter) {
		return AXIOS.post(`/api/list/search-data`, JSON.stringify({
			keyword: keyword,
			current_page: current_page,
			per_page: per_page,
			sort_by: sort_by,
			is_sort_desc: is_sort_desc,
			filter: filter
		}));
	},

	create(keyword, dataList) {
		return AXIOS.post(`/api/create`, JSON.stringify({
			keyword: keyword,
			data_list: dataList
		}));
	},

	update(dataList) {
		return AXIOS.put(`/api/update`, dataList);
	},

	delete(ids) {
		return AXIOS.post(`/api/delete`, ids);
	}
}