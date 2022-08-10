<script>
export let width = null
export let height = null
export let useShadow
export let useBorder
export let cardNode
export let style
export let Class
let shadow
let border

useShadow? (shadow = "0px 3px 17px rgba(0,0,0,0.16)") : null
useBorder? (border = "1px solid rgba(0,0,0,0.16)") : null
width = width!= null? width+"px":"max-content"

</script>


    <div bind:this={cardNode} style="width: {width}; height: {height + "px"}; box-shadow:{shadow}; background-color: white; {style}; border: {border}" class="card {Class}" >
        <slot></slot>
    </div>


<style>
    .card{
        padding: 10px;
        height: max-content;
    }
</style>