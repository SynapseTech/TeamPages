<script setup lang="ts">
	import CopyrightNotice from '../components/CopyrightNotice.vue';
	import { computed, ref } from 'vue';
	import {
		faUser,
		faLock,
		faEye,
		faEyeSlash,
	} from '@fortawesome/free-solid-svg-icons';

	const username = ref<string>('');
	const password = ref<string>('');
	const showPassword = ref<boolean>(false);

	const allowLogIn = computed<boolean>(
		() => password.value !== '' && username.value !== '',
	);

	function submitForm() {
		if (!allowLogIn) return;

		// todo: send login logic.
	}
</script>

<template>
	<div class="loginPage">
		<div class="column">
			<main class="content">
				<h1 class="heading">Log In</h1>

				<form class="loginForm" @submit.prevent="submitForm">
					<div class="formControl">
						<font-awesome-icon
							:icon="faUser"
							fixed-width
							class="icon"
						></font-awesome-icon>
						<input
							name="username"
							id="usernameInput"
							v-model="username"
							placeholder="Username"
							type="text"
						/>
					</div>

					<div class="formControl mt-4">
						<font-awesome-icon
							:icon="faLock"
							fixed-width
							class="icon"
						></font-awesome-icon>
						<input
							name="password"
							id="passwordInput"
							v-model="password"
							placeholder="Password"
							:type="showPassword ? 'text' : 'password'"
						/>
						<font-awesome-icon
							:icon="showPassword ? faEyeSlash : faEye"
							fixed-width
							class="icon cursor-pointer"
							@click="showPassword = !showPassword"
						></font-awesome-icon>
					</div>

					<div class="flex items-center mt-4 w-full">
						<a href="#" class="link flex-grow-0"
							>Forgot your password?</a
						>

						<div class="flex-grow" />

						<button
							type="submit"
							:disabled="!allowLogIn"
							:aria-disabled="!allowLogIn"
							class="button primary flex-grow-0"
							:class="{ disabled: !allowLogIn }"
						>
							Log In
						</button>
					</div>
				</form>
			</main>

			<footer class="footer">
				<CopyrightNotice />
			</footer>
		</div>
	</div>
</template>

<style scoped lang="scss">
	@import '../styles/buttons.scss';
	@import '../styles/forms.scss';
	@import '../styles/text.scss';

	.loginPage {
		@apply flex flex-col items-center w-screen h-screen;

		background-color: #f3f4f6;
		background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='100' height='100' viewBox='0 0 100 100'%3E%3Cg fill-rule='evenodd'%3E%3Cg fill='%23e5e7eb' fill-opacity='1'%3E%3Cpath opacity='.5' d='M96 95h4v1h-4v4h-1v-4h-9v4h-1v-4h-9v4h-1v-4h-9v4h-1v-4h-9v4h-1v-4h-9v4h-1v-4h-9v4h-1v-4h-9v4h-1v-4h-9v4h-1v-4H0v-1h15v-9H0v-1h15v-9H0v-1h15v-9H0v-1h15v-9H0v-1h15v-9H0v-1h15v-9H0v-1h15v-9H0v-1h15v-9H0v-1h15V0h1v15h9V0h1v15h9V0h1v15h9V0h1v15h9V0h1v15h9V0h1v15h9V0h1v15h9V0h1v15h9V0h1v15h4v1h-4v9h4v1h-4v9h4v1h-4v9h4v1h-4v9h4v1h-4v9h4v1h-4v9h4v1h-4v9h4v1h-4v9zm-1 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-9-10h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm9-10v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-9-10h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm9-10v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-9-10h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm9-10v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-10 0v-9h-9v9h9zm-9-10h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9zm10 0h9v-9h-9v9z'/%3E%3Cpath d='M6 5V0H5v5H0v1h5v94h1V6h94V5H6z'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E");

		.column {
			@apply w-full md:w-1/3 lg:w-1/4 mx-auto flex flex-col h-full;

			.content {
				@apply flex-grow flex flex-col justify-center;

				.heading {
					@apply w-full text-center text-6xl font-light mb-8;
				}

				.loginForm {
					@apply rounded-lg shadow-lg bg-white p-4;
				}
			}

			.footer {
				@apply flex-grow-0 flex flex-col items-center text-gray-500;
			}
		}
	}
</style>
