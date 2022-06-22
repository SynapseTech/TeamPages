import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';

export interface LoginResponse {
	success: true;
	message: null;
	token: string;
}

export interface ErrorResponse {
	success: false;
	message: string;
}

export async function login(
	email: string,
	password: string,
): Promise<LoginResponse | ErrorResponse> {
	const authStore = useAuthStore();

	// @ts-ignore
	try {
		const res = await fetch(
			`${import.meta.env.VITE_API_URL}/v1/users/login`,
			{
				method: 'POST',
				body: JSON.stringify({
					email,
					password,
				}),
				headers: {
					'Content-Type': 'application/json',
				},
			},
		);

		if (!res.ok) throw (await res.json()) as ErrorResponse;
		const body = (await res.json()) as LoginResponse;
		authStore.logIn(body.token);
		return body;
	} catch (resData: any) {
		return resData as ErrorResponse;
	}
}
