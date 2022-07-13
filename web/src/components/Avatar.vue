<script setup lang="ts">
	import { computed, useAttrs } from 'vue';
	const attrs = useAttrs();

	interface AvatarProps {
		src: string;
	}

	const props = withDefaults(defineProps<AvatarProps>(), {});

	const avatarClasses = computed(() => ({
		clickable: !!attrs.onClick,
	}));
</script>

<template lang="pug">
.avatar(:class="avatarClasses")
	img(alt="" :src="props.src")
</template>

<style scoped lang="scss">
	.avatar {
		@apply aspect-square h-8 w-8;

		img {
			@apply rounded-full max-h-full max-w-full h-auto w-auto;
		}

		&.clickable {
			@apply cursor-pointer;
		}
	}
</style>
