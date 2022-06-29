import { useAuthStore } from '@/stores/auth';
import type { User } from '@/lib/data/User';

export interface LoginResponse {
	success: true;
	message: null;
	token: string;
}

export interface MeResponse {
	success: true;
	message: null;
	user: User;
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

export async function getMe(): Promise<User> {
	const authStore = useAuthStore();

	const res = await fetch(`${import.meta.env.VITE_API_URL}/v1/users/me`, {
		method: 'GET',
		headers: {
			Authorization: `Bearer ${authStore.token}`,
		},
	});

	if (!res.ok) throw (await res.json()) as ErrorResponse;
	const body = (await res.json()) as MeResponse;
	return body.user;
}
