import { defineStore } from 'pinia';
import { ref } from 'vue';
import type { User } from '@/lib/data/User';
import { getMe } from '@/lib/api/user';

export const useAuthStore = defineStore(
	'auth',
	() => {
		const token = ref<string | null>(null);
		const loggedIn = ref<boolean>(false);
		const currentUser = ref<User>();

		function logIn(loginToken: string) {
			token.value = loginToken;
			loggedIn.value = true;
		}

		function logOut() {
			token.value = null;
			loggedIn.value = false;
			currentUser.value = undefined;
		}

		async function getUser(): User {
			if (!currentUser.value) {
				currentUser.value = await getMe();
			}

			return currentUser.value;
		}

		return { logIn, logOut, token, loggedIn, getUser };
	},
	{ persist: true },
);
