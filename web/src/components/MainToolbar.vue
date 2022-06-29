<script setup lang="ts">
	import { useAuthStore } from '@/stores/auth';
	import Avatar from '@/components/Avatar.vue';
	import { onMounted, ref } from 'vue';
	import type { User } from '@/lib/data/User';
	import { getMe } from '@/lib/api/user';
	import AppIcon from '@/assets/app_icon.svg?component';

	const authStore = useAuthStore();
	const currentUser = ref<User>();

	onMounted(async () => {
		currentUser.value = await getMe();
	});
</script>

<template>
	<nav class="appToolbar">
		<div class="section">
			<div class="brand">
				<AppIcon />
			</div>

			<router-link to="/" class="link" active-class="active">
				Home
				<div class="activeIndicator" />
			</router-link>
			<router-link to="/books" class="link" active-class="active">
				Books
				<div class="activeIndicator" />
			</router-link>
		</div>
		<div class="spacer" />
		<div class="section">
			<div class="currentUser" v-if="authStore.loggedIn">
				<Avatar
					src="https://cdn.discordapp.com/avatars/543542278967394322/99f5040863c94823e743134348722b1c.png"
					class="userAvatar"
				/>
				{{ currentUser?.username }}
			</div>
		</div>
	</nav>
</template>

<style scoped lang="scss">
	@import '../styles/colors';

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
				@apply p-2;

				svg {
					@apply aspect-square h-8 w-8;
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
