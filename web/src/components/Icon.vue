<script setup lang="ts">
	import type { Component } from 'vue';
	import { computed, defineProps, reactive } from 'vue';

	import icons from '@/assets/icons/icons';

	console.log(icons);

	const emit = defineEmits(['tap']);

	interface IconProps {
		name: keyof typeof icons;
		svgClass: string;
		size?: number;
	}

	const props = withDefaults(defineProps<IconProps>(), {
		size: 24,
	});
	const { svgClass, size } = reactive(props);
	const icon = computed<Component>(() => icons[props.name]);

	const dynamicClasses = computed(() => ({
		[svgClass]: true,
	}));

	function tapped() {
		emit('tap');
	}
</script>

<template lang="pug">
div
	KeepAlive
		component.inline(
			:is="icon"
			@click="tapped"
			:class="dynamicClasses"
			:style="{ width: `${size}px`, height: `${size}px` }"
		)
</template>

<style scoped lang="scss"></style>
