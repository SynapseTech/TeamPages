<script setup lang="ts">
	import MainToolbar from '@/components/MainToolbar.vue';
</script>

<template lang="pug">
.indexPage
	MainToolbar.flex-grow-0

	main.content
		aside.sideMenu
			// todo: search box
			.sideList
				.heading Recent Books
</template>

<style lang="scss">
	.indexPage {
		@apply flex flex-col h-screen;

		.content {
			@apply flex-grow flex;

			.sideMenu {
				@apply flex-grow-0 w-72 p-4 border-r-[1px] border-gray-200 self-stretch;

				.sideList {
					.item {
					}

					.heading {
						@apply mb-2;
					}
				}
			}
		}
	}
</style>
