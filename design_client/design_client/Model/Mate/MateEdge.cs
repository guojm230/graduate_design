using System;
using System.Collections.Generic;
using System.Security.Cryptography.X509Certificates;
using System.Text;

namespace design_client.Model.Mate
{
    //邻接多重表形式的边
    public class MateEdge
    {
        public MateVertex Prev { get; protected set; }
        public MateVertex Next { get; protected set; }

        public MateInterface PrevInterface { get; protected set; }
        public MateInterface NextInterface { get; protected set; }

        public MateEdge? PrevLink { get; protected set; }
        public MateEdge? NextLink { get; protected set; }

        public bool Mated { get; protected set; }
        
        //用来标记
        public bool Mark { get; protected set; }

        private MateEdge(MateInterface prevInterface,MateInterface nextInterface,MateEdge? p,MateEdge? n)
        {
            this.Prev = prevInterface.Vertex();
            this.Next = nextInterface.Vertex();
            this.PrevInterface = prevInterface;
            this.NextInterface = nextInterface;
            this.PrevLink = p;
            this.NextLink = n; 
        }

        //创建一条边
        public static MateEdge? createEdge(MateInterface prev,MateInterface next)
        {
            var pv = prev.Vertex();
            var nv = next.Vertex();
            if (!pv.CanSupport(next) || !nv.CanSupport(prev))
                return null;
            MateInterface? m;
            MateEdge? temp,newEdge = new MateEdge(prev,next,null,null);
            newEdge.Mated = (m = pv.Mate(next)) != null;
            if(pv.FirstEdge == null)    //没有表则直接将边设置为firstEdge
            {
                pv.FirstEdge = newEdge;
            } else //有边则找到最后一条以pv为起点的边。将result设置为prevLink
            {
                temp = pv.FirstEdge;
                while (temp.PrevLink != null)
                    temp = temp.PrevLink;
                temp.PrevLink = newEdge;
            }
            //下一条边同理
            if(nv.FirstEdge == null)
            {
                nv.FirstEdge = newEdge;
            } else
            {
                temp = nv.FirstEdge;
                while(temp.NextLink != null)
                    temp = temp.NextLink;
                temp.NextLink = newEdge;
            }
            return newEdge;
        }
    }

}
