using SolidWorks.Interop.sldworks;
using System;
using System.Collections.Generic;
using System.Text;

namespace design_client.Model.Mate
{
    public class MateGraph
    {
        private List<MateVertex> vertices = new List<MateVertex>();

        /// <summary>
        /// 检测是否全部配合完毕
        /// </summary>
        /// <returns>是否配合完毕</returns>
        public bool isMated()
        {
            //直接检测所有边是否配合即可，不需要特殊的遍历规则
            MateEdge? mateEdge = null;
            foreach(var vertex in vertices)
            {
                mateEdge = vertex.FirstEdge;
                while (mateEdge != null)
                {
                    if (!mateEdge.Mated)
                    {
                        return false;
                    }
                    mateEdge = mateEdge.PrevLink;
                }
            }
            return true;
        }
    }
}
