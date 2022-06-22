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

<template>
	<div class="alert" :class="dynamicClasses">
		<div class="alertBody">
			<Icon
				class="closeButton"
				v-if="close"
				name="closeSquare"
				@click="closeButtonClicked"
			/>
			<div class="heading" v-if="icon || heading">
				<Icon class="icon" v-if="icon" :name="icon" />
				<template v-if="heading">
					{{ heading }}
				</template>
			</div>
			<div class="content">
				<slot />
			</div>
		</div>
	</div>
</template>

<style scoped lang="scss">
	@import '../styles/alert.scss';
</style>
