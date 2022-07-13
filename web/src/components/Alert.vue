<script setup lang="ts">
	import type icons from '@/assets/icons/icons';
	import { computed, reactive } from 'vue';
	import Icon from '@/components/Icon.vue';

	interface AlertProps {
		heading?: string;
		icon?: keyof typeof icons;
		close: boolean;
		color?: string;
	}

	const props = withDefaults(defineProps<AlertProps>(), {
		icon: undefined,
		heading: undefined,
		close: false,
		color: undefined,
	});

	const emit = defineEmits(['close']);
	const { heading, icon, close, color } = reactive(props);

	const dynamicClasses = computed(() => ({
		...(color
			? {
					[color]: true,
			  }
			: {}),
	}));

	function closeButtonClicked() {
		emit('close');
	}
</script>

<template lang="pug">
.alert(:class="dynamicClasses")
	.alertBody
		Icon.closeButton(
			v-if="close"
			name="closeSquare"
			@click="closeButtonClicked"
		)
		.heading(v-if="icon || heading")
			Icon.icon(v-if="icon" :name="icon")
			template(v-if="heading") {{ heading }}
		.content
			slot
</template>

<style scoped lang="scss">
	@import '../styles/alert';
</style>
