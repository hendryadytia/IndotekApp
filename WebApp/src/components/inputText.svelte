<script>
     import Svelecte from '../../node_modules/svelecte/src/Svelecte.svelte';
     import {apiurl} from '../helper/apiurl.js'
export let label
export let name
export let id
export let style 
export let value = '' 
export let width = '260px'
export let type = 'text'
export let placeholder 
export let noLabel = false
export let height = 25
export let disabled = ""

let payload = null;
function resetPayload(e) {
    payload = null;
  }
function typeAction(node) {
		node.type = type;
	}
let inputType = "input"
let labelMargin 
let centerVertical = "align-items: center;"
if (height == 25) {
    style = centerVertical + style
} else {
    labelMargin = "margin-top:5px"
    inputType = "Textarea"
}

</script>
<div class="s" style={style}>
    {#if (!noLabel)}
    <p style={labelMargin}> {label}</p>
    {/if} 

    {#if height == 25 && (type == 'text' || type == 'password')}
        <input id={id} class= "input" use:typeAction name={name} style="height: {height}px;  width: {width};" placeholder={placeholder} bind:value={value}  {disabled}>     
    {:else if type == 'date'}
        <input id={id} class= "input" use:typeAction type="date" style="height: {height}px; width: {width};" name={name} 
            placeholder="dd-mm-yyyy" bind:value={value}
            min="1997-01-01" max="2030-12-31" >

    {:else if type == 'autocomplete'}
    <div style="  width: 275px;">
        <Svelecte name="selection"
            on:change={resetPayload}
            required
            bind:value={value}
            placeholder="cari teknisi"
            fetch={apiurl+"teknisisearch?search=[query]"}>
        </Svelecte>
    </div>
        
    {:else}
    <textarea type="text" class= "input" name={name} style="height: {height}px; width: {width}; resize: vertical; min-height:{height}px" bind:value={value}  {disabled} />    
    {/if}
    
</div>

<style>
    .input{
        align-self: center;
       
        
        border: none;
        background-color: #E8E8E8;
        padding: 4px 8px;
        border-radius: 5px;
    }
    p{
        font-size: 16px;
        color: var(--accent-color)
    }
    .s{
        justify-content: space-between;
        width: 100%;
        display: flex;
        flex-direction: row;
    }
    
    
    input:focus{
        outline: var(--accent-color) 2px solid;
    }
</style>