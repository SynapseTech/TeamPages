<script setup lang="ts">
	import type icons from '@/assets/icons/icons';
	import { reactive } from 'vue';
	import Icon from '@/components/Icon.vue';

	interface AlertProps {
		heading?: string;
		icon?: keyof typeof icons;
		close: boolean;
	}

	const props = withDefaults(defineProps<AlertProps>(), {
		icon: undefined,
		heading: undefined,
		close: false,
	});

	const emit = defineEmits(['close']);

	const { heading, icon, close } = reactive(props);

	function closeButtonClicked() {
		emit('close');
	}
</script>

<template>
	<div class="alert">
		<Icon class="icon" v-if="icon" :name="icon" />
		<div class="alertBody">
			<div class="heading" v-if="heading">{{ heading }}</div>
			<div class="content">
				<slot />
			</div>
		</div>
		<Icon
			class="closeButton"
			v-if="close"
			name="closeSquare"
			@click="closeButtonClicked"
		/>
	</div>
</template>

<style scoped lang="scss">
	@import '../styles/alert.scss';
</style>
