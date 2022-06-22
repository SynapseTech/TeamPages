<script setup lang="ts">
	import type { Component } from 'vue';
	import { computed, defineProps, reactive } from 'vue';

	import icons from '@/assets/icons/icons';

	console.log(icons);

	const emit = defineEmits(['tap']);

	interface IconProps {
		name: keyof typeof icons;
		svgClass: string;
	}

	const props = defineProps<IconProps>();
	const { svgClass } = reactive(props);
	const icon = computed<Component>(() => icons[props.name]);

	function tapped() {
		emit('tap');
	}
</script>

<template>
	<div>
		<KeepAlive>
			<component :is="icon" @click="tapped" :class="svgClass" />
		</KeepAlive>
	</div>
</template>

<style scoped lang="scss"></style>
