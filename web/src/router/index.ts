import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/IndexView.vue';

const router = createRouter({
	history: createWebHistory(import.meta.env.BASE_URL),
	routes: [
		{
			path: '/',
			name: 'home',
			component: HomeView,
		},
		{
			path: '/login',
			name: 'login',
			component: () => import('../views/LoginView.vue'),
		},
		{
			path: '/books',
			name: 'books',
			component: () => import('../views/BooksView.vue'),
		},
	],
});

export default router;
