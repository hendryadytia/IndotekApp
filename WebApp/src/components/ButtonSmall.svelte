<script>
    export let style
</script>


    <button class={$$props.class} style={style} on:click|preventDefault>
        <slot/>
    </button>

<style>
    button{
        background-color: var(--accent-color);
        border: none;
        color: white;
        font-size: 14pt;
        padding: 4px 12px;
        font-weight: bold;
        border-radius: 5px;
    }
</style>
