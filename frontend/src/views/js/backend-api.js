import axios from 'axios'

const AXIOS = axios.create({
	baseUrl: `/api`,
	headers: {'Content-Type': 'text/plain'},
	timeout: 30000
});

export default {
	search(keyword) {
		return AXIOS.post(`/api/search`, keyword);
	}
}