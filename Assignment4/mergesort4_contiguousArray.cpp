#include <stdio.h>
#include <stdlib.h>

struct node
{
  int key;
  int link;
};

// void mergesort4(int low, int high, int mergedlist)
// {
//   int mid, list1, list2;

//   if(low == high)
//   {
//     mergedlist = low;
//     // S[mergedlist].link = 0;
//   }
//   else
//   {
//     // mid = Math.floor((low + high)/2);
//     mergesort4(low, mid, list1);
//     mergesort4(mid + 1, high, list2);
//     // merge4(mid + 1, high, mergedlist);
//   }
// }

// void merge4(int list1, int list2, int mergedlist)
// {}

void printList(struct node *S, int mergedlist)
{
  while(true)
  {
    printf("%d ", S[mergedlist].key);

    if(S[mergedlist].link == -1)
      break;
    mergedlist = S[mergedlist].link;
  }
  printf("\n");
}

int main()
{
  int n = 6;
  node S[n];
  int mergedlist = 4;
  int index = 0;

  S[0].key = 3; S[0].link = 5;
  S[1].key = 6; S[1].link = -1;
  S[2].key = 2; S[2].link = 0;
  S[3].key = 5; S[3].link = 1;
  S[4].key = 1; S[4].link = 2;
  S[5].key = 4; S[5].link = 3;

  printf("Sorted Linked List is: \n");
  printList(S, mergedlist);

  for(int i = 1; i <= n; i++)
  {
    if(S[mergedlist].link != -1)
    {
      int tmp = mergedlist;
      mergedlist = S[tmp].link;
      S[tmp].link = i-1;
    }
  }

  /*for(int i = 0; i < n; i++)
    for(int j = 0; j < n; j++)
    {
      if(i != S[j].key)
        S[i] = S[j];
    }*/

  printList(S, 0);

  /*int SS[6];

  while(true)
  {
    SS[index] = S[mergedlist].key;
    index++;

    if(S[mergedlist].link == -1)
      break;
    mergedlist = S[mergedlist].link;
  }

  for(int i = 0; i < 6; i++)
  {
    printf("%d ", SS[i]);
  }
  printf("\n");*/

  // getchar();
  return 0;
}