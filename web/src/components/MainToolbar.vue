<script setup lang="ts">
	import { useAuthStore } from '@/stores/auth';
	import Avatar from '@/components/Avatar.vue';
	import { onMounted, ref } from 'vue';
	import type { User } from '@/lib/data/User';
	import AppIcon from '@/assets/app_icon.svg?component';

	const authStore = useAuthStore();
	const currentUser = ref<User>();

	const showUserMenu = ref<boolean>(false);

	onMounted(async () => {
		currentUser.value = await authStore.getUser();
	});
</script>

<template lang="pug">
nav.appToolbar
	.section
		.brand
			AppIcon
			| TeamPages

		router-link.link(to="/" active-class="active")
			| Home
			.activeIndicator
		router-link.link(to="/books" active-class="active")
			| Books
			.activeIndicator
	.spacer
	.section
		.currentUser(v-if="authStore.loggedIn")
			Avatar(
				src="https://cdn.discordapp.com/avatars/543542278967394322/99f5040863c94823e743134348722b1c.png"
				class="userAvatar"
				ref="avatarComponent"
				@click="showUserMenu = !showUserMenu"
			)

			.userMenu(v-show="showUserMenu")
				.item {{ currentUser?.username }}
</template>

<style scoped lang="scss">
	@import '../styles/colors';

	.userMenu {
		@apply bg-white rounded border-[1px] border-gray-200 shadow flex flex-col absolute bottom-[-8px] right-3 items-center w-48;
		transform: translateY(100%);
		z-index: 1000;

		.item {
			@apply p-2;

			&:not(:last-child) {
				@apply border-b-[1px] border-gray-100;
			}
		}
	}

	.appToolbar {
		@apply flex items-center border-b-[1px] border-gray-200;

		.section {
			@apply flex items-center flex-grow-0;

			.link {
				@apply py-2 px-4 self-stretch flex items-center text-gray-500;

				.activeIndicator {
					@apply hidden rounded-t h-1 absolute bottom-0 left-3 right-3;
					background-color: $primary-500;
				}

				&.active {
					color: $primary-500;

					.activeIndicator {
						@apply block;
					}
				}
			}

			.brand {
				@apply p-2 w-72 flex items-center font-bold tracking-wide;
				color: $primary-500;

				svg {
					@apply aspect-square h-8 w-8 mr-2;
				}
			}

			.currentUser {
				@apply flex items-center p-2;

				.userAvatar {
					@apply mr-2;
				}
			}
		}

		.spacer {
			@apply flex-grow;
		}
	}
</style>
