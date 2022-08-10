<script>
     import {apiurl,apiurlonly} from '../../helper/apiurl.js'
    import Card from '../Card.svelte'
    import DataList from '../DataListForCard.svelte'
    import IMP from '../ImagePlaceHolder.svelte'
    import Table from '../Table.svelte'
    import X from 'jquery'

    export let id


    console.log(id);

    let nama, idt, bergabung,alamat,notelp,keahlian,telahproses,username,fotoprofil,fotoworkshop
    let dataKeahlian = ['Keahlian','Telah Diproses']

    X.ajax({
            url:apiurl+"daftarteknisi",
            type:"GET",
            data: {
                id: id
            },
            beforeSend: function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        },
            success: function(result){
                console.log(result.data);
                nama = result.data.nama
                username = result.data.username
                bergabung = result.data.tgl_dibuat
                fotoprofil = apiurlonly+ result.data.foto_diri
                fotoworkshop = apiurlonly+result.data.foto_workshop
                idt = result.data.id
                notelp = result.data.no_telp
                keahlian = result.data.keahlian
                alamat = result.data.alamat
                telahproses = result.data.jml_diproses||0
                
            }
                        
            },
            )
    function getCookie(cname){
        let name = cname + "=";
        let ca = document.cookie.split(';');
        for(let i = 0;i< ca.length; i++){
            let c = ca[i]
            while (c.charAt(0) == ' '){
                c = c.substring(1)
            }
            if(c.indexOf(name) == 0){
                return c.substring(name.length,c.length)
            }
        }
        return ""
    }



    let dtblCFG =      {
                searching: false, 
                paging: false,
                info: false,
                "ajax":{
                        "url": apiurl+"teknisimesindiproses",
                        "type": "GET",
                        "beforeSend": function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        },
                        "data":{
                            id_teknisi:id
                        }
                        
                    },
                columns: [
                        { data: 'jenis_mesin' },
                        { data: 'jenis_mesin' },
                        { data: 'nama' },
                        { data: 'tgl_selesai' },
                        { data: 'nama_status' },
                        
         
                    ],
                "columnDefs": [ 
                    // field 1 isinya id, di hide
                    {
                        "targets": 0,
                        "visible" : false
                    } ,
                    {
                        "targets": 1,
                        "render" : function ( data, type, row ) {
                            return data.length > 14 ? data.substring(0,13)+"..." : data
                        },
                    } ,
                    {
                        "targets": 2,
                        "render" : function ( data, type, row ) {
                            return data.length > 8 ? data.substring(0,7)+"..." : data
                        },
                    
                    } ,
                    {
                        "targets": 4,
                        "render" : function ( data, type, row ) {
                            return data.length > 8 ? data.substring(0,7)+"..." : data
                        },
                    } 
                ]
            }
    let thead = ['id',
                            'Jenis Mesin',
                            'Pemilik',
                            'Selesai',
                            'Status'
                        ]

</script>
<div style="display: flex; flex-direction:column; gap:8px">

    
    <Card width=500 useBorder> 
        <div style="display: flex; gap:16px">
            <IMP style="min-width: 100px" url={fotoprofil} height=110/>
            <div style="display: flex; flex-direction: column; gap:16px; ">
                
                    <DataList title="Nama" isi={nama}/>
                    <DataList title="Username" isi={username}/>
                    <DataList title="ID Teknisi" isi={idt}/>
                    <DataList title="Tgl Bergabung" isi={bergabung}/>
                
            </div>
        </div>
    </Card>

    <Card width=500 useBorder> 
        <div style="display: flex; gap:16px">
            <IMP width=100 url={fotoworkshop}/>
            <div style="display: flex; flex-direction: column; gap:16px; ">
                <p style="font-weight: bold"> Alamat</p>
                <p> {alamat}</p>
                <p style="line-height: 14px;
                display: flex;
                gap: 5px;"> 
            </div>
        </div>
    </Card>


    <Card width=500 useBorder> 
        <div style="display: flex; flex-direction: column; gap:16px;position: relative">
            
                <DataList title="Keahlian" isi={keahlian}/>
                <DataList title="Telah Diproses" isi={telahproses}/>
 
        </div>
    </Card>

    <Table width=500 dataTableCFG={dtblCFG} tableHead={thead}/>
   
</div>